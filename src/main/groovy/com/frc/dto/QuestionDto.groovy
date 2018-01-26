package com.frc.dto

import groovy.transform.Sortable

@Sortable(includes = ['sequence', 'id'])
class QuestionDto {
    Integer id
    String question
    Integer sequence
    QuestionTypeDto questionType
}
