package com.frc.controller

import com.frc.dto.EventDto
import com.frc.dto.MatchupDto
import com.frc.dto.QuestionDto
import com.frc.dto.QuestionTypeDto
import com.frc.dto.ResponseValueDto
import com.frc.dto.SurveyDto
import com.frc.dto.SurveySectionDto
import com.frc.dto.TeamDto
import com.frc.dto.TeamMatchupDto
import com.frc.entity.Event
import com.frc.entity.Matchup
import com.frc.entity.Question
import com.frc.entity.QuestionType
import com.frc.entity.ResponseValue
import com.frc.entity.Survey
import com.frc.entity.SurveySection
import com.frc.entity.Team
import com.frc.entity.TeamMatchup
import com.frc.repository.EventRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@Slf4j
@RestController
@RequestMapping("/events")
class EventController {

    @Autowired
    EventRepository eventRepository

    @GetMapping(path = '{eventId}/surveys', produces = APPLICATION_JSON_VALUE)
    Set<SurveyDto> getSurveys(@PathVariable(name = 'eventId') Integer eventId) {
        Event event = eventRepository.findOne(eventId)
        event.surveys.collect { convert(it, false) }
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    List<EventDto> getAllEvents() {
        Set<Event> events = eventRepository.findByActive(true)
        events.collect { event ->
            new EventDto(
                    eventId: event.id,
                    name: event.name,
                    city: event.city,
                    state: event.state,
                    startDate: event.startDate
            )
        }
    }

    @GetMapping(path = '{eventId}/surveys/{surveyId}', produces = APPLICATION_JSON_VALUE)
    EventDto getCurrent(
            @PathVariable(name = 'eventId') Integer eventId,
            @PathVariable(name = 'surveyId') Integer surveyId) {

        Event event = eventRepository.findOne(eventId)
        SurveyDto survey = convert(event.surveys, surveyId)
        return new EventDto(
                eventId: event.id,
                survey: survey,
                name: event.name,
                city: event.city,
                state: event.state,
                startDate: event.startDate,
                current: event.current,
                matchups: convertMatchups(event.matchups, survey)
        )
    }

    private static Collection<MatchupDto> convertMatchups(Collection<Matchup> matchups, SurveyDto survey) {
        List<MatchupDto> dtos = []
        matchups.each {
            if (survey.name.contains('Pit') && it.matchNumber == -1) {
                dtos.add(convert(it))
            } else if (survey.name.contains('Match') && it.matchNumber != -1) {
                dtos.add(convert(it))
            }
        }
        return dtos
    }

    private static MatchupDto convert(Matchup matchup) {
        new MatchupDto(
                matchupId: matchup.id,
                startTime: matchup.startTime,
                matchNumber: matchup.matchNumber,
                type: matchup.type,
                teamMatchups: convertTeamMatchups(matchup.teamMatchups)
        )
    }

    private static Collection<TeamMatchupDto> convertTeamMatchups(Collection<TeamMatchup> teamMatchups) {
        teamMatchups.collect {
            new TeamMatchupDto(
                    teamMatchupId: it.id,
                    alliance: it.alliance,
                    team: convert(it.team)
            )
        }
    }

    private static TeamDto convert(Team team) {
        return new TeamDto(
                teamId: team.id,
                name: team.name,
                city: team.city,
                state: team.state,
                school: team.school
        )
    }

    private static SurveyDto convert(Set<Survey> surveys, Integer surveyId) {

        final Survey survey
        if (surveyId) {
            survey = surveys.find { it.id == surveyId }
        } else {
            survey = surveys.find { it.current }
        }

        return convert(survey, true)
    }

    private static SurveyDto convert(Survey survey, boolean includeSections) {
        SurveyDto dto = new SurveyDto(
                surveyId: survey.id,
                name: survey.name,
        )
        if (includeSections) {
            dto.surveySections = convertSurveySections(survey.surveySections)
        }
        return dto
    }

    private static Collection<SurveySectionDto> convertSurveySections(Collection<SurveySection> surveySections) {
        surveySections.collect {
            new SurveySectionDto(
                    surveySectionId: it.id,
                    name: it.name,
                    sequence: it.sequence,
                    questions: convertQuestions(it.questions)
            )
        } as TreeSet
    }

    private static Set<QuestionDto> convertQuestions(Set<Question> questions) {
        questions.collect {
            new QuestionDto(
                    questionId: it.id,
                    question: it.question,
                    sequence: it.sequence,
                    questionType: convert(it.questionType)
            )
        } as TreeSet
    }

    private static QuestionTypeDto convert(QuestionType questionType) {
        new QuestionTypeDto(
                questionTypeId: questionType.id,
                description: questionType.description,
                responseValues: convertResponseValues(questionType.responseValues)
        )
    }

    private static Set<ResponseValueDto> convertResponseValues(Collection<ResponseValue> responseValues) {
        responseValues.collect {
            new ResponseValueDto(
                    responseValueId: it.id,
                    value: it.value
            )
        } as TreeSet
    }
}
