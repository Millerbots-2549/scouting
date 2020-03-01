package com.frc.controller

import com.frc.dto.ResponseDto
import com.frc.entity.*
import com.frc.repository.QuestionRepository
import com.frc.repository.ResponseRepository
import com.frc.repository.StudentRepository
import com.frc.repository.TeamMatchupRepository
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/responses")
class ResponseController {

    private static final String ZERO = '0'

    @Autowired
    ResponseRepository responseRepository
    @Autowired
    TeamMatchupRepository teamMatchupRepository
    @Autowired
    StudentRepository studentRepository
    @Autowired
    QuestionRepository questionRepository

    /**
     * This method is used to store the results of the completed survey into the database. This is called when the user
     * submits the survey.
     *
     * @param responseDtos - this is a collection of all answers provided on the survey. One responseDto per question.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void save(@RequestBody Collection<ResponseDto> responseDtos) {
        Student student = studentRepository.findById(responseDtos.first().studentId).orElse(null)
        TeamMatchup tm = teamMatchupRepository.findById(responseDtos.first().teamMatchupId).orElse(null)
        tm.responseSaved = true

        responseDtos.each {
            Question question = questionRepository.findById(it.questionId).orElse(null)
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

    private static String cleanResponse(String response, Question question) {
        String cleanResponse = StringUtils.trim(StringUtils.stripToEmpty(response))
        // if the response is empty and its numeric set it to zero
        if (!cleanResponse && question.questionType.description.toUpperCase() == QuestionTypeValue.NUMERIC.toString()) {
            return ZERO
        }

        return cleanResponse
    }
}
