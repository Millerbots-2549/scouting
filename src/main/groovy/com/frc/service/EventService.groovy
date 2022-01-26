package com.frc.service

import com.frc.dto.EventDto
import com.frc.dto.MatchupDto
import com.frc.dto.SurveyDto
import com.frc.entity.Event
import com.frc.entity.Matchup
import com.frc.entity.Survey
import com.frc.entity.SurveyType
import com.frc.repository.EventRepository
import com.frc.util.Converter
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Service

import javax.transaction.Transactional
import java.time.LocalDate

@Slf4j
@CompileStatic
@Service
@Transactional
class EventService {

    @Autowired
    EventRepository eventRepository

    Set<EventDto> getActiveEvents() {
        Set<Event> events = eventRepository.findActiveEvents(LocalDate.now())
        events.collect { Converter.convert(it) } as TreeSet
    }

    Set<EventDto> getEvents() {
        List<Event> events = eventRepository.findAll()
        events.collect { Converter.convert(it) } as TreeSet
    }

    @Secured('ROLE_ADMIN')
    EventDto save(EventDto dto) {
        Event entity = Converter.merge(dto, new Event())
        Converter.convert(eventRepository.saveAndFlush(entity))
    }

    @Secured('ROLE_ADMIN')
    EventDto update(Integer eventId, EventDto dto) {
        Event event = eventRepository.getById(eventId)
        Event entity = Converter.merge(dto, event)
        Converter.convert(eventRepository.saveAndFlush(entity))
    }

    @Secured('ROLE_ADMIN')
    void delete(Integer eventId) {
        eventRepository.deleteById(eventId)
    }

    Set<SurveyDto> getEventSurveys(Integer eventId) {
        Event event = getEvent(eventId)
        if (event.surveys) {
            return event.surveys.collect { Converter.convert(it) } as TreeSet
        } else {
            return Collections.emptySet()
        }
    }

    EventDto getEventSurvey(Integer eventId, Integer surveyId) {
        Event event = getEvent(eventId)
        Survey survey = getSurvey(event.surveys, surveyId)

        EventDto eventDto = Converter.convert(event)
        eventDto.survey = convertSurveyWithSections(survey)
        eventDto.matchups = convertMatchups(event.matchups, survey)

        return eventDto
    }

    Event getEvent(int eventId) {
        Event event = eventRepository.findById(eventId).orElse(null)
        if (event == null) {
            throw new RuntimeException("No event found for id=${eventId}")
        }
        event
    }

    private static Survey getSurvey(Set<Survey> surveys, Integer surveyId) {
        Survey survey = surveys.find { it.id == surveyId }
        if (survey == null) {
            throw new RuntimeException("No survey found for id=${surveyId}")
        }
        survey
    }

    private static SurveyDto convertSurveyWithSections(Survey survey) {
        SurveyDto surveyDto = Converter.convert(survey)
        surveyDto.surveySections = survey.surveySections.collect { Converter.convert(it) } as TreeSet
        return surveyDto
    }

    private static Set<MatchupDto> convertMatchups(Collection<Matchup> matchups, Survey survey) {
        Set<MatchupDto> dtos = [] as TreeSet
        matchups.each {
            MatchupDto dto = Converter.convert(it)
            if (dto.teamMatchups) {
                if (survey.getType() == SurveyType.PIT && it.matchNumber == -1) {
                    dtos.add(dto)
                } else if (survey.getType() == SurveyType.MATCH && it.matchNumber != -1) {
                    dtos.add(dto)
                }
            }
        }
        return dtos
    }

}
