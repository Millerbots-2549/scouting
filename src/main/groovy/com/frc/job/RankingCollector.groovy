package com.frc.job

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.frc.dto.blueAlliance.EventRankingDto
import com.frc.dto.blueAlliance.RankingDto
import com.frc.entity.Event
import com.frc.entity.TeamRanking
import com.frc.entity.Team
import com.frc.repository.EventRepository
import com.frc.repository.RankingRepository
import com.frc.repository.TeamRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Slf4j
@Service
class RankingCollector {

    private static final OBJECT_MAPPER = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    @Autowired
    EventRepository eventRepository
    @Autowired
    RankingRepository rankingRepository
    @Autowired
    TeamRepository teamRepository

    // initial delay of 10 minutes and then once an hour after that
    //@Scheduled(fixedRate = 3600000l, initialDelay = 600000l)
    @Scheduled(fixedRate = 600000l, initialDelay = 60000l)
    void getRankings() {
        log.info("Starting the collection of blue alliance rankings")
        Event currentEvent = eventRepository.findByCurrent(true)
        if (currentEvent) {
            collectRankingData(currentEvent)
        }
    }

    private void collectRankingData(Event event) {
        HttpURLConnection connection = BlueAllianceClient.createConnection(event, 'rankings')
        if (connection.responseCode == 200) {
            int count = 0
            EventRankingDto eventRanking = OBJECT_MAPPER.readValue(connection.inputStream, EventRankingDto.class)
            eventRanking?.rankings?.each {
                count++
                updateRanking(it, findTeam(it), event)
            }
            log.info("Collected ${count} rankings")
        }
    }

    private void updateRanking(RankingDto dto, Team team, Event event) {
        TeamRanking ranking = rankingRepository.findByTeamAndEvent(team, event)
        if (ranking) {
            ranking.rank = dto.rank
        } else {
            ranking = new TeamRanking(team: team, event: event, rank: dto.rank)
        }
        rankingRepository.save(ranking)
    }

    private Team findTeam(RankingDto ranking) {
        Integer teamId = ranking?.teamKey?.replaceAll('frc', '')?.trim()?.toInteger()
        if (teamId) {
            return teamRepository.findById(teamId).orElse(null)
        } else {
            return null
        }
    }
}
