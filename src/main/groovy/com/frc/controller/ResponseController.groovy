package com.frc.controller

import com.frc.dto.ResponseDto
import com.frc.service.ResponseService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@CompileStatic
@RestController
@RequestMapping("/responses")
class ResponseController {

    @Autowired
    ResponseService service

    /**
     * This method is used to store the results of the completed survey into the database. This is called when the user
     * submits the survey.
     *
     * @param responseDtos - this is a collection of all answers provided on the survey. One responseDto per question.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void save(@RequestBody Collection<ResponseDto> responseDtos) {
        service.save(responseDtos)
        service.updateEditable(responseDtos)
    }
}
