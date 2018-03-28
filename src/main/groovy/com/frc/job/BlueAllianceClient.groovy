package com.frc.job

import com.fasterxml.jackson.databind.ObjectMapper
import com.frc.dto.blueAlliance.EventRankingDto
import com.frc.dto.blueAlliance.RankingDto
import com.frc.entity.Event
import com.frc.entity.Ranking
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
class BlueAllianceClient {

    private static final objectMapper = new ObjectMapper()

    @Autowired
    EventRepository eventRepository
    @Autowired
    RankingRepository rankingRepository
    @Autowired
    TeamRepository teamRepository

    @Scheduled(fixedRate = 3600000l, initialDelay = 600000l)
    void getRankings() {
        log.info("Starting the collection of blue alliance rankings")
        Event event = eventRepository.findByCurrent(true)
        String eventKey = event.eventKey
        def connection = new URL("https://www.thebluealliance.com/api/v3/event/${eventKey}/rankings").openConnection() as HttpURLConnection

        // set some headers
        connection.setRequestProperty('X-TBA-Auth-Key', 'alXSYHeSPE3hFXTQMzYYYo4vkqra4S5RuvWXgEuPbVqFpCtxkc4paUvJr4OyHOcy')
        connection.setRequestProperty('User-Agent', 'groovy-2.4.4')
        connection.setRequestProperty('Accept', 'application/json')

        if (connection.responseCode == 200) {
            int count = 0
            EventRankingDto eventRanking = objectMapper.readValue(connection.inputStream, EventRankingDto.class)
            eventRanking.rankings.each {
                count++
                updateRanking(it, findTeam(it), event)
            }
            log.info("Collected ${count} rankings")
        }
    }

    private void updateRanking(RankingDto dto, Team team, Event event){
        Ranking ranking = rankingRepository.findByTeamAndEvent(team, event)
        if (ranking) {
            ranking.rank = dto.rank
        } else {
            ranking = new Ranking(team: team, event: event, rank: dto.rank)
        }
        rankingRepository.save(ranking)
    }

    private Team findTeam(RankingDto ranking) {
        Integer teamId = ranking.teamKey.replaceAll('frc', '').trim().toInteger()
        teamRepository.findOne(teamId)
    }


}
