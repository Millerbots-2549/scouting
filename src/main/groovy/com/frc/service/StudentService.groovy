package com.frc.service

import com.frc.dto.StudentDto
import com.frc.entity.RoleType
import com.frc.entity.Student
import com.frc.entity.StudentRole
import com.frc.entity.Team
import com.frc.repository.StudentRepository
import com.frc.repository.TeamRepository
import com.frc.util.Converter
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@CompileStatic
@Slf4j
@Service
@Transactional
class StudentService {

    private static final int DEFAULT_TEAM_ID = 2549

    @Autowired
    TeamRepository teamRepository
    @Autowired
    StudentRepository studentRepository
    @Autowired
    PasswordEncoder passwordEncoder

    Set<StudentDto> getActiveStudents() {
        studentRepository.findByActive(true).collect { Converter.convert(it) } as TreeSet
    }

    Set<StudentDto> getAll() {
        studentRepository.findAll().collect { Converter.convert(it) } as TreeSet
    }

    Student getAuthenticatedStudent() {
        String username = SecurityContextHolder.getContext().authentication.name
        Student student = studentRepository.findByUsername(username)
        if (student == null) {
            throw new RuntimeException("Not able to find student with username=${username}")
        }
        student
    }

    @Secured('ROLE_ADMIN')
    StudentDto save(StudentDto dto) {
        dto.roleUser = true
        Student entity = Converter.merge(dto, new Student())
        entity.team = teamRepository.getById(DEFAULT_TEAM_ID)
        entity.password = passwordEncoder.encode(dto.password)
        Converter.convert(studentRepository.saveAndFlush(entity))
    }

    @Secured('ROLE_ADMIN')
    StudentDto update(Integer id, StudentDto dto) {
        dto.roleUser = true
        Student student = studentRepository.getById(id)
        // if there is a password, then it was changed. otherwise it would be blank
        String password = StringUtils.stripToNull(dto.password)
        if (password) {
            student.password = passwordEncoder.encode(password)
        }
        Converter.merge(dto, student)

        syncRoles(student, RoleType.ROLE_USER, dto.roleUser)
        syncRoles(student, RoleType.ROLE_POWER_USER, dto.rolePowerUser)
        syncRoles(student, RoleType.ROLE_ADMIN, dto.roleAdmin)

        Converter.convert(studentRepository.saveAndFlush(student))
    }

    private static void syncRoles(Student student, RoleType roleType, boolean add) {
        boolean exists = student.roles.any { it.role == roleType }
        if (add && !exists) {
            student.roles.add(new StudentRole(student: student, role: roleType))
        } else if (!add && exists) {
            for (StudentRole role : student.roles) {
                if (role.role == roleType) {
                    student.roles.removeElement(role)
                    break
                }
            }
        }
    }

}
