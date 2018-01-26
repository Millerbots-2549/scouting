package com.frc.repository

import com.frc.entity.Question
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository extends JpaRepository<Question, Integer> {
}
