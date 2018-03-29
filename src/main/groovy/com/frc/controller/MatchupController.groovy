package com.frc.controller

import com.frc.dto.view.MatchDto
import com.frc.entity.*
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
import org.springframework.web.bind.annotation.ResponseBody

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

    @GetMapping('/load')
    @ResponseBody
    Map load() {
        Map<String, String> results = [:]
        Integer count = 0
        URI uri = this.class.classLoader.getResource("data/matchUps.txt").toURI()
        File file = new File(uri)
        file.eachLine { String line ->
            count++
            List<String> items = line.split('[|]')

            MatchDto dto = new MatchDto()

            dto.matchNumber = items[0].replace('Quals ', '').trim().toInteger()
            dto.teamNumberRedOne = items[1].toInteger()
            dto.teamNumberRedTwo = items[2].toInteger()
            dto.teamNumberRedThree = items[3].toInteger()

            dto.teamNumberBlueOne = items[4].toInteger()
            dto.teamNumberBlueTwo = items[5].toInteger()
            dto.teamNumberBlueThree = items[6].toInteger()

            Date startDateTime = new Date().parse('M/d/yyyy h:mm a', items[7])

            dto.startDate = startDateTime.format("yyyy-MM-dd")
            dto.startTime = startDateTime.format("HH:mm")

            saveData(dto)
        }
        results.put('Number of records', count.toString())
        return results
    }

    @PostMapping
    String createMatchup(@ModelAttribute MatchDto dto, final Model model) {
        saveData(dto)
        displayMatchup(model)
    }

    private void saveData(MatchDto dto) {
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
    }

    private void updateTeamMatchups(Matchup matchup, MatchDto dto) {
        Set<TeamMatchup> newMatchups = [
                new TeamMatchup(alliance: Alliance.RED.name().toLowerCase(), matchup: matchup, team: teamRepository.findOne(dto.teamNumberRedOne), responseSaved: false),
                new TeamMatchup(alliance: Alliance.RED.name().toLowerCase(), matchup: matchup, team: teamRepository.findOne(dto.teamNumberRedTwo), responseSaved: false),
                new TeamMatchup(alliance: Alliance.RED.name().toLowerCase(), matchup: matchup, team: teamRepository.findOne(dto.teamNumberRedThree), responseSaved: false),
                new TeamMatchup(alliance: Alliance.BLUE.name().toLowerCase(), matchup: matchup, team: teamRepository.findOne(dto.teamNumberBlueOne), responseSaved: false),
                new TeamMatchup(alliance: Alliance.BLUE.name().toLowerCase(), matchup: matchup, team: teamRepository.findOne(dto.teamNumberBlueTwo), responseSaved: false),
                new TeamMatchup(alliance: Alliance.BLUE.name().toLowerCase(), matchup: matchup, team: teamRepository.findOne(dto.teamNumberBlueThree), responseSaved: false)
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
