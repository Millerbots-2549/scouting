package com.frc.dto

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable

@CompileStatic
@EqualsAndHashCode
@Sortable(includes = ['sequence', 'surveySectionId'])
class SurveySectionDto {
    Integer surveySectionId
    String name
    Integer sequence
    Set<QuestionDto> questions
}
