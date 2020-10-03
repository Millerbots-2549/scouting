package com.frc.service

import com.frc.dto.ResponseDto
import com.frc.entity.*
import com.frc.repository.QuestionRepository
import com.frc.repository.ResponseRepository
import com.frc.repository.StudentRepository
import com.frc.repository.TeamMatchupRepository
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
@Transactional
@Slf4j
class ResponseService {

    private static final String ZERO = '0'

    @Autowired
    ResponseRepository responseRepository
    @Autowired
    TeamMatchupRepository teamMatchupRepository
    @Autowired
    StudentRepository studentRepository
    @Autowired
    QuestionRepository questionRepository

    void save(Collection<ResponseDto> responseDtos) {
        Student student = getStudent(SecurityContextHolder.getContext().authentication.name)
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

    private TeamMatchup getTeamMatchup(Collection<ResponseDto> responseDtos) {
        TeamMatchup tm = teamMatchupRepository.findById(responseDtos.first().teamMatchupId).orElse(null)

        if (tm == null) {
            throw new RuntimeException("Not able to find team match up with id=${responseDtos.first().teamMatchupId}")
        }
        tm
    }

    private Student getStudent(String username) {
        Student student = studentRepository.findByUsername(username)
        if (student == null) {
            throw new RuntimeException("Not able to find student with username=${username}")
        }
        student
    }

    private Question getQuestion(ResponseDto it) {
        Question question = questionRepository.findById(it.questionId).orElse(null)
        if (question == null) {
            throw new RuntimeException("Not able to find question with id=${it.questionId}")
        }
        question
    }

    private static String cleanResponse(String response, Question question) {
        String cleanResponse = StringUtils.trim(StringUtils.stripToEmpty(response))
        // if the response is empty and its numeric set it to zero
        if (!cleanResponse && question.questionType.description.toUpperCase() == QuestionTypeValue.NUMERIC.toString()) {
            return ZERO
        }

        return cleanResponse
    }
}
