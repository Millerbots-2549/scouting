package com.frc.job

import com.frc.dto.blueAlliance.EventRankingDto
import com.frc.dto.blueAlliance.RankingDto
import com.frc.entity.Event
import com.frc.entity.Team
import com.frc.entity.TeamRanking
import com.frc.repository.RankingRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

import javax.net.ssl.HttpsURLConnection
import javax.transaction.Transactional
import java.time.LocalDate

@Slf4j
//@CompileStatic
@Transactional
@Service
class RankingCollector extends BlueAllianceClient {

    @Autowired
    RankingRepository rankingRepository

    // initial delay of 10 minutes and then once an hour after that
    @Scheduled(fixedRate = 3600000l, initialDelay = 600000l)
    int getRankings() {
        int rankingsCollected = 0
        if (ENABLED) {
            log.debug("Starting the collection of blue alliance rankings")
            Set<Event> events = eventRepository.findActiveEvents(LocalDate.now())
            events?.each { event -> rankingsCollected += collectRankingData(event) }
            log.debug("Done with the collection of blue alliance rankings")
        }
        return rankingsCollected
    }

    private int collectRankingData(Event event) {
        String endPoint = 'rankings'
        int count = 0
        HttpsURLConnection connection = createConnection(event, endPoint)
        if (connection?.responseCode == 200) {
            EventRankingDto eventRanking = OBJECT_MAPPER.readValue(connection.inputStream, EventRankingDto.class)
            eventRanking?.rankings?.each { ranking ->
                count++
                updateRanking(ranking, findTeam(ranking?.teamKey), event)
            }
            updateLastModified(endPoint, connection)
            log.info("Collected ${count} rankings")
        }
        return count
    }

    private void updateRanking(RankingDto dto, Team team, Event event) {
        if (team && event) {
            TeamRanking ranking = rankingRepository.findByTeamAndEvent(team, event)
            if (ranking) {
                ranking.rank = dto.rank
            } else {
                ranking = new TeamRanking(team: team, event: event, rank: dto.rank)
            }
            rankingRepository.save(ranking)
        }
    }

}
