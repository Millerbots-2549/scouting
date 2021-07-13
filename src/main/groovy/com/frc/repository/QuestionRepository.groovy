package com.frc.repository

import com.frc.entity.Question
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository

@CompileStatic
interface QuestionRepository extends JpaRepository<Question, Integer> {
}
