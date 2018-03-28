package com.frc.controller

import com.frc.dto.StudentDto
import com.frc.entity.Student
import com.frc.repository.StudentRepository
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
        studentRepository.findByActive(true).collect { convert(it) } as TreeSet
    }

    private static StudentDto convert(Student student) {
        new StudentDto(
                studentId: student.id,
                firstName: student.firstName,
                lastName: student.lastName
        )
    }
}
