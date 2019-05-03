package com.frc.dto

import groovy.transform.Sortable

@Sortable(includes = ['name', 'surveyId'])
class SurveyDto {
    Integer surveyId
    String name
    Set<SurveySectionDto> surveySections
}
