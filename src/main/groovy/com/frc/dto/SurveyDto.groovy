package com.frc.dto

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable

@CompileStatic
@EqualsAndHashCode
@Sortable(includes = ['name', 'surveyId'])
class SurveyDto {
    Integer surveyId
    String name
    String type
    String year
    Set<SurveySectionDto> surveySections
}
