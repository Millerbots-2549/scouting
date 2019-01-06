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
import com.frc.job.RankingCollector
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
    @Autowired
    RankingCollector blueAllianceClient

    @GetMapping(path = '/rankings', produces = APPLICATION_JSON_VALUE)
    String getRankings() {
        blueAllianceClient.getRankings()
        "Done"
    }

    @GetMapping(path = '/{eventId}/surveys', produces = APPLICATION_JSON_VALUE)
    Set<SurveyDto> getSurveys(@PathVariable(name = 'eventId') Integer eventId) {
        Event event = eventRepository.findById(eventId).orElse(null)
        event.surveys.collect { convert(it) }
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    List<EventDto> getAllEvents() {
        Set<Event> events = eventRepository.findByActive(true)
        events.collect { convert(it) }
    }

    @GetMapping(path = '/{eventId}/surveys/{surveyId}', produces = APPLICATION_JSON_VALUE)
    EventDto getEventSurvey(
            @PathVariable(name = 'eventId') Integer eventId,
            @PathVariable(name = 'surveyId') Integer surveyId) {

        Event event = eventRepository.findById(eventId).orElse(null)
        Survey survey = findSurvey(event.surveys, surveyId)
        SurveyDto surveyDto = convert(survey)
        surveyDto.surveySections = survey.surveySections.collect { convert(it) } as TreeSet

        EventDto eventDto = convert(event)
        eventDto.survey = surveyDto
        eventDto.matchups = convertMatchups(event.matchups, survey)

        return eventDto
    }

    private static Survey findSurvey(Set<Survey> surveys, Integer surveyId) {
        final Survey survey
        if (surveyId) {
            survey = surveys.find { it.id == surveyId }
        } else {
            survey = surveys.find { it.current }
        }
        return survey
    }

    private static Collection<MatchupDto> convertMatchups(Collection<Matchup> matchups, Survey survey) {
        List<MatchupDto> dtos = []
        matchups.each {
            MatchupDto dto = convert(it)
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

    private static Collection<TeamMatchupDto> convertTeamMatchups(Collection<TeamMatchup> teamMatchups) {
        List<TeamMatchupDto> dtos = []
        teamMatchups.each {
            if (!it.responseSaved) {
                dtos.add(convert(it))
            }
        }
        return dtos
    }

    /*-----------------------*/
    /* Individual Converters */
    /*-----------------------*/

    private static EventDto convert(Event event) {
        return new EventDto(
                eventId: event.id,
                name: event.name,
                city: event.city,
                state: event.state,
                startDate: event.startDate,
                current: event.current
        )
    }

    private static SurveyDto convert(Survey survey) {
        new SurveyDto(
                surveyId: survey.id,
                name: survey.name
        )
    }

    private static TeamMatchupDto convert(TeamMatchup tm) {
        new TeamMatchupDto(
                teamMatchupId: tm.id,
                alliance: tm.alliance,
                team: convert(tm.team)
        )
    }

    private static SurveySectionDto convert(SurveySection ss) {
        new SurveySectionDto(
                surveySectionId: ss.id,
                name: ss.name,
                sequence: ss.sequence,
                questions: ss.questions.collect { convert(it) } as TreeSet
        )
    }

    private static QuestionDto convert(Question q) {
        new QuestionDto(
                questionId: q.id,
                question: q.question,
                sequence: q.sequence,
                questionType: convert(q.questionType)
        )
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

    private static TeamDto convert(Team team) {
        return new TeamDto(
                teamId: team.id,
                name: team.name,
                city: team.city,
                state: team.state,
                school: team.school
        )
    }

    private static QuestionTypeDto convert(QuestionType questionType) {
        new QuestionTypeDto(
                questionTypeId: questionType.id,
                description: questionType.description,
                responseValues: questionType.responseValues.collect { convert(it) } as TreeSet
        )
    }

    private static ResponseValueDto convert(ResponseValue rv) {
        new ResponseValueDto(
                responseValueId: rv.id,
                value: rv.value,
                isDefault: rv.isDefault
        )
    }
}
