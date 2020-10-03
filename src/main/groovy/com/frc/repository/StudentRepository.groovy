package com.frc.repository

import com.frc.entity.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository extends JpaRepository<Student, Integer> {

    Set<Student> findByActive(Boolean active)

    Student findByUsername(String username)
}
