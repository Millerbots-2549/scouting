package com.frc.controller

import com.frc.dto.ResponseDto
import com.frc.entity.Response
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

        List<Response> responses = responseDtos.collect {
            new Response(
                    question: questionRepository.findOne(it.questionId),
                    teamMatchup: teamMatchupRepository.findOne(it.teamMatchupId),
                    student: studentRepository.findOne(it.studentId),
                    response: it.response.trim()
            )
        }

        responseRepository.save(responses)
    }
}
