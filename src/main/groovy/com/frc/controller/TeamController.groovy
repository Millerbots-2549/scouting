package com.frc.controller

import com.frc.dto.TeamDto
import com.frc.entity.Team
import com.frc.repository.TeamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping(value = "/teams")
class TeamController {

    @Autowired
    TeamRepository teamRepository

    @GetMapping
    String displayTeam(@RequestParam(required=false) Integer id, final Model model) {
        TeamDto dto = new TeamDto()
        if (id) {
            Team team = teamRepository.findOne(id)
            dto.teamId = team.id
            dto.name = team.name
            dto.city = team.city
            dto.state = team.state
            dto.school = team.school
        }

        model.addAttribute('dto', dto)
        return 'teams'
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

        teamRepository.save(team)
        displayTeam(null, model)
    }

}
