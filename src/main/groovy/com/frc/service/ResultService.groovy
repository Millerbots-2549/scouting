package com.frc.service

import com.frc.dto.TeamDto
import com.frc.dto.view.QuestionResultDto
import com.frc.dto.view.ResponseResultDto
import com.frc.dto.view.ResultDto
import com.frc.dto.view.SurveyResultDto
import com.frc.entity.Event
import com.frc.entity.QuestionTypeValue
import com.frc.entity.Response
import com.frc.entity.Team
import com.frc.repository.EventRepository
import com.frc.repository.TeamRepository
import com.frc.util.Converter
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@CompileStatic
@Slf4j
@Service
@Transactional
class ResultService {

    @Autowired
    TeamRepository teamRepository
    @Autowired
    EventRepository eventRepository

    @Secured(['ROLE_POWER_USER','ROLE_ADMIN'])
    Set<TeamDto> getTeams(Integer eventId) {
        Set<TeamDto> teamDtos = [] as TreeSet
        Event event = eventRepository.getOne(eventId)
        event.matchups.each { matchup ->
            matchup.teamMatchups.each { teamMatchup ->
                teamDtos.add(Converter.convert(teamMatchup.team))
            }
        }
        teamDtos
    }

    @Secured(['ROLE_POWER_USER','ROLE_ADMIN'])
    ResultDto getResults(Integer eventId, Integer teamId) {

        Team team = teamRepository.findById(teamId).orElse(null)
        Map<SurveyResultDto, Map<Integer, List<QuestionResultDto>>> questions = [:]
        ResultDto result = new ResultDto(teamName: team.name, teamId: team.id)
        getQuestionData(eventId, team, questions)

        questions.each { surveyDto, questionMap ->
            result.surveys.add(surveyDto)
            questionMap.each { questionId, questionList ->
                QuestionResultDto questionDto = buildQuestionResult(questionList)
                surveyDto.questions.add(questionDto)
            }
            surveyDto.questions.sort()
        }

        result.surveys.sort()

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

    private static String calculateAverage(List<String> responses) {
        double count = 0
        double total = 0
        responses.each {
            count++
            double value = it.trim() ? it.trim().toFloat() : 0
            total += value
        }

        if (count == 0) {
            return '0'
        } else {
            double avg = (total / count).round(2)
            return avg.toString()
        }
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

    private static void getQuestionData(Integer eventId, Team team, Map<SurveyResultDto, Map<Integer, List<QuestionResultDto>>> questions) {
        team.teamMatchups.each { teamMatchUp ->
            if (teamMatchUp.matchup.event.id == eventId) {
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
