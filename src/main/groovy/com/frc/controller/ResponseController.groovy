package com.frc.controller

import com.frc.dto.ResponseDto
import com.frc.entity.QuestionTypeValue
import com.frc.entity.Response
import com.frc.entity.Student
import com.frc.entity.TeamMatchup
import com.frc.repository.QuestionRepository
import com.frc.repository.ResponseRepository
import com.frc.repository.StudentRepository
import com.frc.repository.TeamMatchupRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/responses")
class ResponseController {

    @Autowired
    ResponseRepository responseRepository
    @Autowired
    TeamMatchupRepository teamMatchupRepository
    @Autowired
    StudentRepository studentRepository
    @Autowired
    QuestionRepository questionRepository

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void save(@RequestBody Collection<ResponseDto> responseDtos) {
        Student student = studentRepository.findById(responseDtos.first().studentId).orElse(null)
        TeamMatchup tm = teamMatchupRepository.findById(responseDtos.first().teamMatchupId).orElse(null)
        tm.responseSaved = true

        responseDtos.each {
            Response response = new Response(
                    question: questionRepository.findById(it.questionId).orElse(null),
                    teamMatchup: tm,
                    student: student,
                    response: it.response.trim()
            )
            if (response.question.questionType.description.toUpperCase() == QuestionTypeValue.NUMERIC.toString() && !response.response) {
                response.response = 0
            }
            tm.responses.add(response)
        }

        teamMatchupRepository.save(tm)
    }
}
