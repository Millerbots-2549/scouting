package com.frc.controller

import com.frc.dto.EventDto
import com.frc.dto.MatchupDto
import com.frc.dto.SurveyDto
import com.frc.entity.Event
import com.frc.entity.Matchup
import com.frc.entity.Survey
import com.frc.repository.EventRepository
import com.frc.util.Converter
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.time.LocalDate

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@Slf4j
@RestController
@RequestMapping("/events")
class EventController {

    @Autowired
    EventRepository eventRepository

    @GetMapping(path = '/{eventId}/surveys', produces = APPLICATION_JSON_VALUE)
    Set<SurveyDto> getSurveys(@PathVariable(name = 'eventId') Integer eventId) {
        Event event = eventRepository.findById(eventId).orElse(null)
        if (event && event.surveys) {
            return event?.surveys?.collect { Converter.convert(it) } as TreeSet
        } else {
            log.warn("No event or surveys found given eventId=${eventId}")
            return [] as TreeSet
        }
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    Set<EventDto> getAllEvents() {
        Set<Event> events = eventRepository.findActiveEvents(LocalDate.now())
        events.collect { Converter.convert(it) } as TreeSet
    }

    @GetMapping(path = '/{eventId}/surveys/{surveyId}', produces = APPLICATION_JSON_VALUE)
    EventDto getEventSurvey(
            @PathVariable(name = 'eventId') Integer eventId,
            @PathVariable(name = 'surveyId') Integer surveyId) {

        Event event = eventRepository.findById(eventId).orElse(null)
        Survey survey = findSurvey(event.surveys, surveyId)

        EventDto eventDto = Converter.convert(event)
        eventDto.survey = convertFullSurvey(survey)
        eventDto.matchups = convertMatchups(event.matchups, survey)

        return eventDto
    }

    private static Survey findSurvey(Set<Survey> surveys, Integer surveyId) {
        return surveys.find { it.id == surveyId }
    }

    private static SurveyDto convertFullSurvey(Survey survey) {
        SurveyDto surveyDto = Converter.convert(survey)
        surveyDto.surveySections = survey.surveySections.collect { Converter.convert(it) } as TreeSet
        return surveyDto
    }

    private static Collection<MatchupDto> convertMatchups(Collection<Matchup> matchups, Survey survey) {
        List<MatchupDto> dtos = []
        matchups.each {
            MatchupDto dto = Converter.convert(it)
            if (dto.teamMatchups) {
                if (survey.name.contains('Pit') && it.matchNumber == -1) {
                    dtos.add(dto)
                } else if (survey.name.contains('Match') && it.matchNumber != -1) {
                    dtos.add(dto)
                }
            }
        }
        return dtos
    }


}
