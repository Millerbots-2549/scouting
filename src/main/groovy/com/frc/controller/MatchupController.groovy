package com.frc.controller

import com.frc.dto.view.MatchDto
import com.frc.entity.Alliance
import com.frc.entity.Event
import com.frc.entity.Matchup
import com.frc.entity.MatchupType
import com.frc.entity.TeamMatchup
import com.frc.repository.EventRepository
import com.frc.repository.MatchupRepository
import com.frc.repository.TeamMatchupRepository
import com.frc.repository.TeamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(value = "/matchups")
class MatchupController {

    @Autowired
    MatchupRepository matchupRepository
    @Autowired
    EventRepository eventRepository
    @Autowired
    TeamRepository teamRepository
    @Autowired
    TeamMatchupRepository teamMatchupRepository

    @GetMapping
    String displayMatchup(final Model model) {
        MatchDto dto = new MatchDto()
        Event event = eventRepository.findByCurrent(true)
        dto.startDate = (new Date()).format('yyyy-MM-dd')
        dto.eventName = event.name
        model.addAttribute('dto', dto)
        return 'matchups'
    }

    @PostMapping
    String createMatchup(@ModelAttribute MatchDto dto, final Model model) {

        Matchup matchup = new Matchup(
                matchNumber: dto.matchNumber,
                startTime: Date.parse("yyyy-MM-dd HH:mm", "${dto.startDate} ${dto.startTime}"),
                type: MatchupType.QUALIFIER.name().toLowerCase(),
                event: eventRepository.findByCurrent(true),
                teamMatchups: [] as Set
        )

        // If matchup exists then update it
        Matchup m = matchupRepository.findByMatchNumberAndTypeAndEvent(matchup.matchNumber, matchup.type, matchup.event)
        if (m != null) {
            m.startTime = matchup.startTime
            matchup = m
        }

        updateTeamMatchups(matchup, dto)

        matchupRepository.save(matchup)
        displayMatchup(model)
    }

    private void updateTeamMatchups(Matchup matchup, MatchDto dto) {
        Set<TeamMatchup> newMatchups = [
                new TeamMatchup(alliance: Alliance.RED.name().toLowerCase(), matchup: matchup, team: teamRepository.findOne(dto.teamNumberRedOne)),
                new TeamMatchup(alliance: Alliance.RED.name().toLowerCase(), matchup: matchup, team: teamRepository.findOne(dto.teamNumberRedTwo)),
                new TeamMatchup(alliance: Alliance.RED.name().toLowerCase(), matchup: matchup, team: teamRepository.findOne(dto.teamNumberRedThree)),
                new TeamMatchup(alliance: Alliance.BLUE.name().toLowerCase(), matchup: matchup, team: teamRepository.findOne(dto.teamNumberBlueOne)),
                new TeamMatchup(alliance: Alliance.BLUE.name().toLowerCase(), matchup: matchup, team: teamRepository.findOne(dto.teamNumberBlueTwo)),
                new TeamMatchup(alliance: Alliance.BLUE.name().toLowerCase(), matchup: matchup, team: teamRepository.findOne(dto.teamNumberBlueThree))
        ]

        Set<TeamMatchup> deletes = [] as Set
        matchup.teamMatchups.each { existing ->
            TeamMatchup found = newMatchups.find { it.team.id == existing.team.id }
            if (found) {
                existing.alliance = found.alliance
            } else {
                deletes += existing
            }
        }

        matchup.teamMatchups.removeAll(deletes)

        newMatchups.each { newOne ->
            TeamMatchup found = matchup.teamMatchups.find { it.team.id == newOne.team.id }
            if (!found) {
                matchup.teamMatchups.add(newOne)
            }
        }
    }


}
