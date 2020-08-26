package com.frc.controller

import com.frc.dto.EventDto
import com.frc.dto.TeamDto
import com.frc.dto.view.ResultDto
import com.frc.service.EventService
import com.frc.service.ResultService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping(value = "/results")
class ResultController {

    @Autowired
    ResultService service
    @Autowired
    EventService eventService


    /**
     *
     * This method builds a dto for all the results for a specific team. The results include all the match and pit
     * scouting for the currently active events.
     *
     * @param teamId
     * @return
     */
    @GetMapping('/events/{eventId}/teams/{teamId}')
    ResultDto getResults(@PathVariable('eventId') Integer eventId,
                         @PathVariable('teamId') Integer teamId) {
        service.getResults(eventId, teamId)
    }

    @GetMapping('/events/{eventId}/teams')
    Set<TeamDto> getTeams(@PathVariable('eventId') Integer eventId) {
        service.getTeams(eventId)
    }

    @GetMapping('/events')
    Set<EventDto> getEvents() {
        eventService.getEvents()
    }

}
