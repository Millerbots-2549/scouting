package com.frc.util

import com.frc.dto.*
import com.frc.entity.*
import spock.lang.Specification

import java.time.Instant
import java.time.LocalDate

class ConverterTest extends Specification {

    def "convert team matchups"() {
        given:
        Collection<TeamMatchup> teamMatchups = [new TeamMatchup(id: 1, alliance: 'RED', team: newTeam()),
                                                new TeamMatchup(id: 2, alliance: 'BLUE', team: newTeam())]

        when:
        Collection<TeamMatchupDto> results = Converter.convertTeamMatchups(teamMatchups as Set<TeamMatchup>)

        then:
        results.size() == 2
    }

    def "convert null team matchups"() {
        expect:
        [] as TreeSet == Converter.convertTeamMatchups(null)
    }

    def "convert empty team matchups"() {
        expect:
        [] as TreeSet == Converter.convertTeamMatchups([] as Set<TeamMatchup>)
    }

    def 'convert null student'() {
        expect:
        null == Converter.convert(null)
    }

    def 'convert student'() {
        given:
        Student student = new Student(
                id: 1,
                firstName: 'Bob',
                lastName: 'BobLastName',
                active: true)

        when:
        StudentDto result = Converter.convert(student)

        then:
        result.firstName == student.firstName
        result.lastName == student.lastName
        result.studentId == student.id
    }

    def "convert event"() {
        given:
        Event event = new Event(
                id: 1,
                name: 'event1',
                city: 'City2',
                state: 'state3',
                startDate: LocalDate.of(2020, 1, 1),
                endDate: LocalDate.of(2020, 1, 10))

        when:
        EventDto result = Converter.convert(event)

        then:
        result.eventId == event.id
        result.name == 'event1 2020'
        result.city == event.city
        result.state == event.state
        result.startDate == event.startDate
        result.endDate == event.endDate
    }

    def "convert survey"() {
        given:
        Survey survey = new Survey(id: 1, name: 'name1')

        when:
        SurveyDto result = Converter.convert(survey)

        then:
        result.surveyId == survey.id
        result.name == survey.name
    }

    def "convert team matchup"() {
        given:
        TeamMatchup teamMatchup = new TeamMatchup(id: 1, alliance: 'RED', team: newTeam())

        when:
        TeamMatchupDto result = Converter.convert(teamMatchup)

        then:
        result.teamMatchupId == teamMatchup.id
        result.alliance == teamMatchup.alliance
        result.team
        result.team.teamId == teamMatchup.team.id
    }

    def "convert survey section"() {
        given:
        SurveySection section = new SurveySection(id: 1, name: 'Name1', sequence: 1, questions: [])

        when:
        SurveySectionDto result = Converter.convert(section)

        then:
        result.surveySectionId == section.id
        result.name == section.name
        result.sequence == section.sequence
        result.questions.size() == section.questions.size()
    }

    def "convert question"() {
        given:
        Question question = new Question(
                id: 1,
                question: 'Why?',
                sequence: 1,
                type: 'TEXT',
                choiceGroup: new ChoiceGroup())

        when:
        QuestionDto result = Converter.convert(question)

        then:
        result.questionId == question.id
        result.question == question.question
        result.type == question.type.name()
        result.sequence == question.sequence
    }

    def "convert matchup"() {
        given:
        Matchup matchup = new Matchup(id: 1, startTime: Instant.now(), matchNumber: 1, type: 'PIT', teamMatchups: [])

        when:
        MatchupDto result = Converter.convert(matchup)

        then:
        result.matchupId == matchup.id
        result.startTime == matchup.startTime
        result.matchNumber == matchup.matchNumber
        result.type == matchup.type
        result.teamMatchups.size() == matchup.teamMatchups.size()
    }

    def "convert team"() {
        given:
        Team team = newTeam()

        when:
        TeamDto result = Converter.convert(team)

        then:
        result.teamId == team.id
        result.name == team.name
        result.state == team.state
        result.city == team.city
        result.school == team.school
    }

    def "convert choice group"() {
        given:
        ChoiceGroup choiceGroup = new ChoiceGroup(id: 1, description: 'bob', choices: [])

        when:
        ChoiceGroupDto result = Converter.convert(choiceGroup)

        then:
        result.choiceGroupId == choiceGroup.id
        result.description == choiceGroup.description
        result.choices.size() == choiceGroup.choices.size()
    }

    def "convert choice"() {
        given:
        Choice choice = new Choice(id: 1, value: 'bob', isDefault: true)

        when:
        ChoiceDto result = Converter.convert(choice)

        then:
        result.choiceId == choice.id
        result.value == choice.value
        result.isDefault == choice.isDefault
    }

    private static Team newTeam() {
        return new Team(
                id: 34,
                name: 'Name3',
                city: 'City2',
                state: 'ST',
                school: 'School1'
        )
    }

}
