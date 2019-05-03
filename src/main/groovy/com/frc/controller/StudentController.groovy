package com.frc.controller

import com.frc.dto.StudentDto
import com.frc.repository.StudentRepository
import com.frc.util.Converter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/students')
class StudentController {

    @Autowired
    StudentRepository studentRepository

    @GetMapping
    Collection<StudentDto> getActive() {
        studentRepository.findByActive(true).collect { Converter.convert(it) } as TreeSet
    }

}
