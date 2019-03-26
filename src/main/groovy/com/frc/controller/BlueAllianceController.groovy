package com.frc.controller

import com.frc.job.BlueAllianceClient
import com.frc.job.MatchupCollector
import com.frc.job.RankingCollector
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@Slf4j
@RestController
@RequestMapping('/tba')
class BlueAllianceController {

    @Autowired
    RankingCollector rankingCollector
    @Autowired
    MatchupCollector matchupCollector

    @GetMapping(path = '/rankings', produces = APPLICATION_JSON_VALUE)
    String getRankings() {
        rankingCollector.getRankings()
        'Done'
    }

    @GetMapping(path = '/matchups', produces = APPLICATION_JSON_VALUE)
    String getMatchups() {
        matchupCollector.getMatchups()
        'Done'
    }

    @GetMapping(path = '/disable', produces = APPLICATION_JSON_VALUE)
    String disable() {
        BlueAllianceClient.ENABLED = false
        'DISABLED'
    }

    @GetMapping(path = '/enable', produces = APPLICATION_JSON_VALUE)
    String enable() {
        BlueAllianceClient.ENABLED = true
        'ENABLED'
    }

    @GetMapping(path = '/status', produces = APPLICATION_JSON_VALUE)
    String getStatus() {
        "ENABLED = ${BlueAllianceClient.ENABLED.toString()}"
    }
}
