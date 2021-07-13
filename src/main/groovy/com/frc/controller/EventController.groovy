package com.frc.controller

import com.frc.dto.EventDto
import com.frc.dto.SurveyDto
import com.frc.service.EventService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@Slf4j
@CompileStatic
@RestController
@RequestMapping("/events")
class EventController {

    @Autowired
    EventService service

    @GetMapping(path = '/{eventId}/surveys', produces = APPLICATION_JSON_VALUE)
    Set<SurveyDto> getSurveys(@PathVariable(name = 'eventId', required = true) Integer eventId) {
        service.getEventSurveys(eventId)
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    Set<EventDto> getAllEvents() {
        service.getActiveEvents()
    }

    @GetMapping(path = '/{eventId}/surveys/{surveyId}', produces = APPLICATION_JSON_VALUE)
    EventDto getEventSurvey(
            @PathVariable(name = 'eventId', required = true) Integer eventId,
            @PathVariable(name = 'surveyId', required = true) Integer surveyId) {

        service.getEventSurvey(eventId, surveyId)
    }


}
