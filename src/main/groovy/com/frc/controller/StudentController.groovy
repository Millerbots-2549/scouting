package com.frc.controller

import com.frc.dto.StudentDto
import com.frc.service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/students')
class StudentController {

    @Autowired
    StudentService service

    @GetMapping
    Collection<StudentDto> getActive() {
        service.getActiveStudents()
    }

}
