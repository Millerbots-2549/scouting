package com.frc.service

import com.frc.dto.ResponseDto
import com.frc.entity.*
import com.frc.repository.QuestionRepository
import com.frc.repository.TeamMatchupRepository
import com.frc.util.Converter
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@CompileStatic
@Slf4j
@Service
@Transactional
class ResponseService {

    private static final String ZERO = '0'

    @Autowired
    TeamMatchupRepository teamMatchupRepository
    @Autowired
    StudentService studentService
    @Autowired
    QuestionRepository questionRepository

    void save(Collection<ResponseDto> responseDtos) {
        Student student = studentService.getAuthenticatedStudent()
        TeamMatchup tm = getTeamMatchup(responseDtos)
        tm.responseSaved = true

        responseDtos.each {
            Question question = getQuestion(it)

            Response response = new Response(
                    question: question,
                    teamMatchup: tm,
                    student: student,
                    response: cleanResponse(it.response, question)
            )

            tm.responses.add(response)
        }

        teamMatchupRepository.save(tm)
    }

    @Async
    void updateEditable(Collection<ResponseDto> responseDtos) {
        try {
            responseDtos.each {
                Question question = getQuestion(it)
                question.editable = false
                question?.choiceGroup?.editable = false
                question.surveySection.survey.editable = false
                questionRepository.save(question)
            }
        } catch (Exception e) {
            log.info('Error processing updates for editable', e)
        }
    }

    private TeamMatchup getTeamMatchup(Collection<ResponseDto> responseDtos) {
        TeamMatchup tm = teamMatchupRepository.findById(responseDtos.first().teamMatchupId).orElse(null)

        if (tm == null) {
            throw new RuntimeException("Not able to find team match up with id=${responseDtos.first().teamMatchupId}")
        }
        tm
    }

    private Question getQuestion(ResponseDto it) {
        return questionRepository.findById(it.questionId).orElseThrow()
    }

    private static String cleanResponse(String response, Question question) {
        String cleanResponse = Converter.stripNonAscii(response)
        // if the response is empty and its numeric set it to zero
        if (!cleanResponse && question.type == QuestionType.NUMERIC) {
            return ZERO
        }

        return cleanResponse
    }
}
