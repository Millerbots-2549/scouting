package com.frc.controller

import com.frc.dto.view.QuestionResultDto
import com.frc.dto.view.ResponseResultDto
import com.frc.dto.view.ResultDto
import com.frc.dto.view.SurveyResultDto
import com.frc.entity.QuestionTypeValue
import com.frc.entity.Response
import com.frc.entity.Team
import com.frc.repository.TeamRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping(value = "/results")
class ResultController {

    @Autowired
    TeamRepository teamRepository

    @GetMapping('/{teamId}')
    ResultDto getResults(@PathVariable("teamId") Integer teamId) {

        Team team = teamRepository.findById(teamId).orElse(null)
        if (!team) {
            return new ResultDto(teamId: teamId, teamName: 'No data found')
        }

        Map<SurveyResultDto, Map<Integer, List<QuestionResultDto>>> questions = [:]
        ResultDto result = new ResultDto(teamName: team.name, teamId: team.id)
        getQuestionData(team, questions)

        questions.each { surveyDto, questionMap ->
            result.surveys.add(surveyDto)
            questionMap.each { questionId, questionList ->
                QuestionResultDto questionDto = buildQuestionResult(questionList)
                surveyDto.questions.add(questionDto)
            }
            surveyDto.questions.sort()
        }

        return result
    }

    private static QuestionResultDto buildQuestionResult(List<QuestionResultDto> questionList) {
        QuestionResultDto dto = new QuestionResultDto()
        dto.questionType = questionList.first().questionType
        dto.question = questionList.first().question
        dto.questionId = questionList.first().questionId
        dto.sectionSeq = questionList.first().sectionSeq
        dto.questionSeq = questionList.first().questionSeq

        questionList.each {
            dto.responses.addAll(it.responses)
        }

        dto.responses.sort()

        calculateSummary(dto)

        return dto
    }

    private static void calculateSummary(QuestionResultDto dto) {
        List<String> responses = dto.responses.collect { it.response }
        switch (dto.questionType) {
            case QuestionTypeValue.NUMERIC:
                dto.summary = calculateAverage(responses)
                break
            case QuestionTypeValue.BOOLEAN:
            case QuestionTypeValue.CHOICE:
            case QuestionTypeValue.RADIO:
                dto.summary = calculateMostOccurring(responses)
                break
            default:
                dto.summary = ''
        }
    }

    private static Integer calculateAverage(List<String> responses) {
        int count = 0
        int total = 0
        responses.each {
            count++
            int value = it.trim() ? it.trim().toInteger() : 0
            total += value
        }
        return total / count
    }

    private static String calculateMostOccurring(List<String> responses) {
        Map<String, Integer> counts = [:]
        responses.each {
            if (!counts.containsKey(it)) {
                counts.put(it, 1)
            } else {
                counts.put(it, counts.get(it) + 1)
            }
        }
        int max = 0
        String result = ''
        counts.each { key, count ->
            if (max < count) {
                result = key
                max = count
            }
        }
        return result
    }

    private static ResponseResultDto buildResult(Response response) {
        new ResponseResultDto(response: response.response,
                matchup: response.teamMatchup.matchup.matchNumber,
                studentName: "${response.student.firstName} ${response.student.lastName}")
    }

    private static void getQuestionData(Team team, Map<SurveyResultDto, Map<Integer, List<QuestionResultDto>>> questions) {
        team.teamMatchups.each { teamMatchUp ->
            if (teamMatchUp.matchup.event.isActive()) {
                teamMatchUp.responses.each { response ->
                    populateQuestions(response, questions)
                }
            }
        }
    }

    private static void populateQuestions(Response response, Map<SurveyResultDto, Map<Integer, List<QuestionResultDto>>> questions) {
        Integer questionId = response.question.id

        QuestionResultDto questionDto = buildQuestionDto(response)
        SurveyResultDto surveyDto = buildSurveyDto(response)

        if (!questions.containsKey(surveyDto)) {
            questions.put(surveyDto, [:])
        }
        Map q = questions.get(surveyDto)
        if (!q.containsKey(questionId)) {
            q.put(questionId, [questionDto])
        } else {
            q.get(questionId).add(questionDto)
        }
    }

    private static SurveyResultDto buildSurveyDto(Response response) {
        new SurveyResultDto(
                surveyId: response.question.surveySection.survey.id,
                surveyName: response.question.surveySection.survey.name
        )
    }

    private static QuestionResultDto buildQuestionDto(Response response) {
        new QuestionResultDto(
                question: response.question.question,
                questionId: response.question.id,
                responses: [buildResult(response)],
                questionType: QuestionTypeValue.valueOf(response.question.questionType.description.toUpperCase()),
                questionSeq: response.question.sequence,
                sectionSeq: response.question.surveySection.sequence
        )
    }
}
