package com.frc.dto

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable

@CompileStatic
@EqualsAndHashCode
@Sortable(includes = ['sequence', 'questionId'])
class QuestionDto {
    Integer questionId
    String question
    Integer sequence
    QuestionTypeDto questionType
}
