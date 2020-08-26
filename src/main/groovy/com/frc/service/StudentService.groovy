package com.frc.service

import com.frc.dto.StudentDto
import com.frc.repository.StudentRepository
import com.frc.util.Converter
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
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

}
