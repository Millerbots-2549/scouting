package com.frc.util

import com.frc.dto.*
import com.frc.entity.*
import groovy.transform.CompileStatic
import org.apache.commons.lang3.StringUtils

import java.text.Normalizer

@CompileStatic
class Converter {

    static def convert(Object object) {
        if (object) {
            throw new RuntimeException("no valid converter for ${object.class.name}")
        } else {
            return null
        }
    }

    static Set<TeamMatchupDto> convertTeamMatchups(Set<TeamMatchup> teamMatchups) {
        Set<TeamMatchupDto> dtos = [] as TreeSet
        teamMatchups?.each {
            if (it && !it.responseSaved) {
                dtos.add(convert(it))
            }
        }
        return dtos
    }

    // ------------------------------------------------------

    static StudentDto convert(Student student) {
        if (student) {
            new StudentDto(
                    studentId: student.id,
                    firstName: student.firstName,
                    lastName: student.lastName,
                    username: student.username,
                    password: '',
                    enabled: student.active,
                    roleAdmin: student.roles.any { it.role == RoleType.ROLE_ADMIN },
                    rolePowerUser: student.roles.any { it.role == RoleType.ROLE_POWER_USER },
                    roleUser: student.roles.any { it.role == RoleType.ROLE_USER }
            )
        } else {
            return null
        }
    }

    static EventDto convert(Event event) {
        if (event) {
            EventDto dto = convertForMaintenance(event)
            dto.name = "${event.name} ${event.startDate.year}"
            return dto
        } else {
            return null
        }
    }

    static EventDto convertForMaintenance(Event event) {
        if (event) {
            return new EventDto(
                    eventId: event.id,
                    name: event.name,
                    city: event.city,
                    state: event.state,
                    startDate: event.startDate,
                    eventKey: event.eventKey,
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
                    name: survey.name,
                    type: survey.type,
                    year: survey.year
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
                    allianceOrder: tm.allianceOrder,
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
                    type: q.type.name(),
                    choiceGroup: convert(q.choiceGroup)
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

    static ChoiceGroupDto convert(ChoiceGroup choiceGroup) {
        if (choiceGroup) {
            new ChoiceGroupDto(
                    choiceGroupId: choiceGroup.id,
                    description: choiceGroup.description,
                    choices: choiceGroup.choices.collect { convert(it) } as TreeSet
            )
        } else {
            return null
        }
    }

    static ChoiceDto convert(Choice rv) {
        if (rv) {
            new ChoiceDto(
                    choiceId: rv.id,
                    value: rv.value,
                    isDefault: rv.isDefault
            )
        } else {
            return null
        }
    }

    // ------------------------------------------------------

    static Event merge(EventDto dto, Event entity) {
        entity.id = dto.eventId
        entity.city = stripNonAscii(dto.city)
        entity.endDate = dto.endDate
        entity.eventKey = stripNonAscii(dto.eventKey) ?: entity.eventKey
        entity.name = stripNonAscii(dto.name)
        entity.startDate = dto.startDate
        entity.state = stripNonAscii(dto.state)
        return entity
    }

    static Student merge(StudentDto dto, Student entity) {
        entity.id = dto.studentId
        entity.firstName = stripNonAscii(dto.firstName)
        entity.lastName = stripNonAscii(dto.lastName)
        entity.username = stripNonAscii(dto.username)
        entity.active = dto.enabled

        // only mess with roles for a new student
        if (entity.id == null) {
            entity.roles = [] as HashSet
            if (dto.roleUser) {
                entity.roles.add(new StudentRole(student: entity, role: RoleType.ROLE_USER))
            }
            if (dto.rolePowerUser) {
                entity.roles.add(new StudentRole(student: entity, role: RoleType.ROLE_POWER_USER))
            }
            if (dto.roleAdmin) {
                entity.roles.add(new StudentRole(student: entity, role: RoleType.ROLE_ADMIN))
            }
        }

        return entity
    }

    static String stripNonAscii(final String comment) {
        String s = StringUtils.trim(StringUtils.stripToNull(comment))
        if (s) {
            return Normalizer.normalize(s, Normalizer.Form.NFKD).replaceAll('[^ -~]', '')
        }
        return s
    }

}
