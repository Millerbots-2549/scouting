package com.frc.util

import com.frc.dto.*
import com.frc.entity.*

class Converter {

    static def convert(Object object) {
        if (object) {
            throw new RuntimeException("no valid converter for ${object.class.name}")
        } else {
            return null
        }
    }

    static Collection<TeamMatchupDto> convertTeamMatchups(Collection<TeamMatchup> teamMatchups) {
        List<TeamMatchupDto> dtos = []
        teamMatchups?.each {
            if (it && !it.responseSaved) {
                dtos.add(convert(it))
            }
        }
        return dtos
    }

    static StudentDto convert(Student student) {
        if (student) {
            new StudentDto(
                    studentId: student.id,
                    firstName: student.firstName,
                    lastName: student.lastName
            )
        } else {
            return null
        }
    }

    static EventDto convert(Event event) {
        if (event) {
            return new EventDto(
                    eventId: event.id,
                    name: event.name,
                    city: event.city,
                    state: event.state,
                    startDate: event.startDate,
                    endDate: event.endDate
            )
        } else {
            return null
        }
    }

    static SurveyDto convert(Survey survey) {
        if (survey) {
            new SurveyDto(
                    surveyId: survey.id,
                    name: survey.name
            )
        } else {
            return null
        }
    }

    static TeamMatchupDto convert(TeamMatchup tm) {
        if (TeamMatchup) {
            new TeamMatchupDto(
                    teamMatchupId: tm.id,
                    alliance: tm.alliance,
                    team: convert(tm.team)
            )
        } else {
            return null
        }
    }

    static SurveySectionDto convert(SurveySection ss) {
        if (SurveySection) {
            new SurveySectionDto(
                    surveySectionId: ss.id,
                    name: ss.name,
                    sequence: ss.sequence,
                    questions: ss.questions.collect { convert(it) } as TreeSet
            )
        } else {
            return null
        }
    }

    static QuestionDto convert(Question q) {
        if (q) {
            new QuestionDto(
                    questionId: q.id,
                    question: q.question,
                    sequence: q.sequence,
                    questionType: convert(q.questionType)
            )
        } else {
            return null
        }
    }

    static MatchupDto convert(Matchup matchup) {
        if (matchup) {
            new MatchupDto(
                    matchupId: matchup.id,
                    startTime: matchup.startTime,
                    matchNumber: matchup.matchNumber,
                    type: matchup.type,
                    teamMatchups: convertTeamMatchups(matchup.teamMatchups)
            )
        } else {
            return null
        }
    }

    static TeamDto convert(Team team) {
        if (team) {
            return new TeamDto(
                    teamId: team.id,
                    name: team.name,
                    city: team.city,
                    state: team.state,
                    school: team.school
            )
        } else {
            return null
        }
    }

    static QuestionTypeDto convert(QuestionType questionType) {
        if (questionType) {
            new QuestionTypeDto(
                    questionTypeId: questionType.id,
                    description: questionType.description,
                    responseValues: questionType.responseValues.collect { convert(it) } as TreeSet
            )
        } else {
            return null
        }
    }

    static ResponseValueDto convert(ResponseValue rv) {
        if (rv) {
            new ResponseValueDto(
                    responseValueId: rv.id,
                    value: rv.value,
                    isDefault: rv.isDefault
            )
        } else {
            return null
        }
    }

}
