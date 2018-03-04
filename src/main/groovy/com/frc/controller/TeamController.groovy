package com.frc.controller

import com.frc.dto.TeamDto
import com.frc.entity.Team
import com.frc.repository.TeamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@Controller
@RequestMapping(value = "/teams")
class TeamController {

    @Autowired
    TeamRepository teamRepository

    @GetMapping
    String displayTeam(final Model model) {
        TeamDto dto = new TeamDto()
        model.addAttribute('dto', dto)
        return 'teams'
    }

    @GetMapping(path = '/{id}', produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    TeamDto displayTeam(@PathVariable("id") Integer id) {
        Team team = teamRepository.findOne(id)
        new TeamDto(
                teamId: id,
                name: team?.name,
                city: team?.city,
                state: team?.state,
                school: team?.school,
                country: team?.country
        )
    }

    @PostMapping
    String createTeam(@ModelAttribute TeamDto dto, final Model model) {

        Team team = teamRepository.findOne(dto.teamId)
        if (!team) {
            team = new Team()
        }

        team.id = dto.teamId
        team.name = dto.name
        team.city = dto.city
        team.state = dto.state
        team.school = dto.school
        team.country = dto.country

        teamRepository.save(team)
        displayTeam(model)
    }

}
