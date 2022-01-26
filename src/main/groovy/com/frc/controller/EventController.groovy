package com.frc.controller

import com.frc.dto.EventDto
import com.frc.dto.SurveyDto
import com.frc.service.EventService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

import javax.validation.ValidationException

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

    @GetMapping(path = "/active", produces = APPLICATION_JSON_VALUE)
    Set<EventDto> getActiveEvents() {
        service.getActiveEvents()
    }

    @GetMapping(path = '/{eventId}/surveys/{surveyId}', produces = APPLICATION_JSON_VALUE)
    EventDto getEventSurvey(
            @PathVariable(name = 'eventId', required = true) Integer eventId,
            @PathVariable(name = 'surveyId', required = true) Integer surveyId) {

        service.getEventSurvey(eventId, surveyId)
    }

    @Secured(['ROLE_ADMIN'])
    @GetMapping
    Set<EventDto> getEvents() {
        service.getEvents()
    }

    @Secured('ROLE_ADMIN')
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    EventDto create(@RequestBody EventDto dto) {
        validate(dto)
        return service.save(dto)
    }

    @Secured('ROLE_ADMIN')
    @PutMapping(value = '/{eventId}', produces = APPLICATION_JSON_VALUE)
    EventDto update(@PathVariable(name = 'eventId', required = true) Integer eventId, @RequestBody EventDto dto) {
        validate(dto)
        return service.update(eventId, dto)
    }

    @Secured('ROLE_ADMIN')
    @DeleteMapping(value = '/{eventId}', produces = APPLICATION_JSON_VALUE)
    void delete(@PathVariable(name = 'eventId', required = true) Integer eventId) {
        service.delete(eventId)
    }

    static private validate(EventDto dto) {
        if (dto.startDate >= dto.endDate) {
            throw new ValidationException("Start Date must be before End Date")
        }
    }

}
