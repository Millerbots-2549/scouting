package com.frc.controller

import com.frc.dto.MatchupDto
import com.frc.entity.Matchup
import com.frc.entity.TeamMatchup
import com.frc.repository.EventRepository
import com.frc.repository.MatchupRepository
import com.frc.repository.TeamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping(value = "/matchups")
class MatchupController {

    @Autowired
    MatchupRepository matchupRepository
    @Autowired
    EventRepository eventRepository
    @Autowired
    TeamRepository teamRepository

    @GetMapping
    String displayMatchup(final Model model) {
        model.addAttribute('matchup', new MatchupDto())
        return 'matchups'
    }

    @PostMapping
    String createMatchup(@RequestParam Map<String,Object> data, final Model model) {

        Matchup matchup = new Matchup(
                matchNumber: data.matchNumber as Integer,
                startTime: dto.startTime,
                type: 'qualifier',
                event: eventRepository.findByCurrent(true)
        )

        matchup.teamMatchups = dto.teamMatchups.collect {
            new TeamMatchup(alliance: it.alliance, matchup: matchup, team: teamRepository.findOne(it.team.teamId))
        } as Set

        matchupRepository.save(matchup)
        model.addAttribute('matchup', new MatchupDto())
        return 'matchups'
    }
}
