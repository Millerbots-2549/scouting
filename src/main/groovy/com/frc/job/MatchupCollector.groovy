package com.frc.job

import com.frc.entity.*
import com.frc.repository.MatchupRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

import javax.transaction.Transactional
import java.time.Instant
import java.time.LocalDate

@Slf4j
@Transactional
@Service
class MatchupCollector extends BlueAllianceClient {

    @Autowired
    MatchupRepository matchupRepository

    // initial delay of 10 minutes and then once every 15 minutes after that
    @Scheduled(fixedRate = 900000l, initialDelay = 600000l)
    int getMatchups() {
        if (ENABLED) {
            log.debug("Starting the collection of blue alliance matchups")
            Set<Event> events = eventRepository.findActiveEvents(LocalDate.now())
            events?.each { event ->
                collectTeamEventData(event)
                collectMatchupData(event)
            }
            log.debug("Done with the collection of blue alliance matchups")
            return events ? events.size() : 0
        }
        return 0
    }

    private void collectTeamEventData(Event event) {
        HttpURLConnection connection = createConnection(event, 'teams/simple')
        if (connection && connection.responseCode == 200) {
            int count = 0
            List teams = OBJECT_MAPPER.readValue(connection.inputStream, List.class)

            Matchup pitMatchup = getPitMatchup(event)

            teams?.each { match ->
                count++
                convertToPitMatchup(match as Map, pitMatchup)
            }
            log.info("Collected ${count} teams for pit matches")
        }
    }

    private void collectMatchupData(Event event) {
        HttpURLConnection connection = createConnection(event, 'matches/simple')
        if (connection && connection.responseCode == 200) {
            int count = 0
            List matches = OBJECT_MAPPER.readValue(connection.inputStream, List.class)

            matches?.each { match ->
                count++
                convertToMatchup(match as Map, event)
            }
            log.info("Collected ${count} matches")
        }
    }

    private Matchup getPitMatchup(Event event) {
        Matchup existingMatchup = matchupRepository.findByMatchNumberAndTypeAndEvent(-1, 'pit', event)
        if (existingMatchup) {
            return existingMatchup
        } else {
            Matchup matchUp = new Matchup(
                    startTime: Instant.now(),
                    matchNumber: -1,
                    event: event,
                    type: 'pit'
            )
            return matchupRepository.save(matchUp)
        }
    }

    private void convertToPitMatchup(Map<String, String> teamDto, Matchup matchup) {
        Team team = convertTeam(teamDto)

        TeamMatchup tm = new TeamMatchup(
                alliance: 'pit',
                matchup: matchup,
                responseSaved: false,
                team: team)

        TeamMatchup found = matchup.teamMatchups.find {
            it.team.id == team.id
        }

        if (!found) {
            matchup.teamMatchups.add(tm)
        }
    }

    private Team convertTeam(Map<String, String> teamDto) {
        Team team = findTeam(teamDto.key)
        if (!team) {
            team = new Team(
                    id: teamDto.team_number as Integer,
                    name: teamDto.nickname,
                    city: teamDto.city,
                    state: teamDto.state_prov,
                    country: teamDto.country
            )
            team = teamRepository.save(team)
        }
        return team
    }

    private void convertToMatchup(Map<String, String> match, Event event) {
        long time = match.time as long
        Matchup matchUp = new Matchup(
                startTime: Instant.ofEpochSecond(time),
                matchNumber: match.match_number as Integer,
                event: event,
                type: findMatchupType(match.comp_level as String),
                redScore: convertScore(match.alliances.red.score as String),
                blueScore: convertScore(match.alliances.blue.score as String))

        match.alliances.red.team_keys.each {
            TeamMatchup tm = createTeamMatchup(matchUp, it as String, Alliance.RED.name().toLowerCase(), matchUp.redScore)
            matchUp.teamMatchups.add(tm)
        }
        match.alliances.blue.team_keys.each {
            TeamMatchup tm = createTeamMatchup(matchUp, it as String, Alliance.BLUE.name().toLowerCase(), matchUp.blueScore)
            matchUp.teamMatchups.add(tm)
        }
        saveOrUpdate(matchUp)
    }

    private void saveOrUpdate(Matchup matchup) {
        if (matchup.type == MatchupType.QUALIFIER.name().toLowerCase()) {
            Matchup existingMatchp = matchupRepository.findByMatchNumberAndTypeAndEvent(matchup.matchNumber, matchup.type, matchup.event)
            if (existingMatchp) {
                if (!existingMatchp.blueScore || !existingMatchp.redScore) {
                    existingMatchp.blueScore = matchup.blueScore
                    existingMatchp.redScore = matchup.redScore
                    matchupRepository.save(existingMatchp)
                }
            } else {
                matchupRepository.save(matchup)
            }
        }
    }

    private static Integer convertScore(String score) {
        if (score) {
            Integer s = score as Integer
            if (s < 0) {
                return null
            } else {
                return s
            }
        } else {
            return null
        }
    }

    private TeamMatchup createTeamMatchup(Matchup matchup, String teamKey, String alliance, Integer score) {
        return new TeamMatchup(
                alliance: alliance,
                matchup: matchup,
                responseSaved: score != null,
                team: findTeam(teamKey))
    }

    private static String findMatchupType(String compLevel) {
        switch (compLevel) {
            case 'qm': return MatchupType.QUALIFIER.name().toLowerCase()
            case 'sf': return MatchupType.SEMIFINAL.name().toLowerCase()
            case 'qf': return MatchupType.QUARTERFINAL.name().toLowerCase()
            case 'f': return MatchupType.FINAL.name().toLowerCase()
            default: null
        }
    }
}
