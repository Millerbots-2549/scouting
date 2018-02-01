package com.frc.dto

import groovy.transform.Sortable

@Sortable(includes = ['sequence', 'surveySectionId'])
class SurveySectionDto {
    Integer surveySectionId
    String name
    Integer sequence
    Set<QuestionDto> questions
}
