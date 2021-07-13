package com.frc.controller

import com.frc.dto.StudentDto
import com.frc.service.StudentService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@RestController
@CompileStatic
@Slf4j
@RequestMapping('/students')
class StudentController {

    @Autowired
    StudentService service

    @GetMapping
    @Secured(['ROLE_ADMIN'])
    Collection<StudentDto> getAll() {
        service.getAll()
    }

    @Secured('ROLE_ADMIN')
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    StudentDto create(@RequestBody StudentDto dto) {
        return service.save(dto)
    }

    @Secured('ROLE_ADMIN')
    @PutMapping(value = '/{studentId}', produces = APPLICATION_JSON_VALUE)
    StudentDto update(@PathVariable(name = 'studentId', required = true) Integer studentId, @RequestBody StudentDto dto) {
        return service.update(studentId, dto)
    }

    @Secured('ROLE_ADMIN')
    @DeleteMapping(value = '/{studentId}', produces = APPLICATION_JSON_VALUE)
    void delete(@PathVariable(name = 'studentId', required = true) Integer studentId) {

    }

}
