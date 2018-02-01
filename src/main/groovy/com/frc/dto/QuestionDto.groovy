package com.frc.dto

import groovy.transform.Sortable

@Sortable(includes = ['sequence', 'questionId'])
class QuestionDto {
    Integer questionId
    String question
    Integer sequence
    QuestionTypeDto questionType
}
