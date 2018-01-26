package com.frc.dto

import groovy.transform.Sortable

@Sortable(includes = ['sequence', 'id'])
class SurveySectionDto {
    Integer id
    String name
    Integer sequence
    Set<QuestionDto> questions
}
