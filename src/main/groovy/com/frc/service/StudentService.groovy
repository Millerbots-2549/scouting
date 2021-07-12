package com.frc.service

import com.frc.dto.StudentDto
import com.frc.entity.Student
import com.frc.repository.StudentRepository
import com.frc.util.Converter
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
@Transactional
@Slf4j
class StudentService {

    @Autowired
    StudentRepository studentRepository

    Set<StudentDto> getActiveStudents() {
        studentRepository.findByActive(true).collect { Converter.convert(it) } as TreeSet
    }

    Student getAuthenticatedStudent() {
        String username = SecurityContextHolder.getContext().authentication.name
        Student student = studentRepository.findByUsername(username)
        if (student == null) {
            throw new RuntimeException("Not able to find student with username=${username}")
        }
        student
    }

}
