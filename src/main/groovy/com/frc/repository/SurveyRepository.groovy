package com.frc.repository

import com.frc.entity.Survey
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository

@CompileStatic
interface SurveyRepository extends JpaRepository<Survey, Integer> {

    Set<Survey> findByYear(int year)
}