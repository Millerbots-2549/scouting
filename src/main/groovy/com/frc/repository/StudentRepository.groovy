package com.frc.repository

import com.frc.entity.Student
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository

@CompileStatic
interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByUsername(String username)
}
