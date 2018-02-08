package com.frc.controller

import com.frc.dto.*
import com.frc.entity.*
import com.frc.repository.EventRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/events")
class EventController {

    @Autowired
    EventRepository eventRepository

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    EventDto getCurrent() {
        Event event = eventRepository.findByCurrent(true)

         return new EventDto(
                eventId: event.id,
                survey: convert(event.survey),
                name: event.name,
                city: event.city,
                state: event.state,
                startDate: event.startDate,
                current: event.current,
                matchups: convertMatchups(event.matchups)
        )
    }

    private static Collection<MatchupDto> convertMatchups(Collection<Matchup> matchups) {
        matchups.collect {
            new MatchupDto(
                    matchupId: it.id,
                    startTime: it.startTime,
                    matchNumber: it.matchNumber,
                    type: it.type,
                    teamMatchups: convertTeamMatchups(it.teamMatchups)
            )
        }
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

    private static SurveyDto convert(Survey survey) {
        return new SurveyDto(
                surveyId: survey.id,
                surveySections: convertSurveySections(survey.surveySections)
        )
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