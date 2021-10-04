package com.frc.dto

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable

@CompileStatic
@EqualsAndHashCode
@Sortable(includes = ['description', 'questionTypeId'])
class QuestionTypeDto {
    Integer questionTypeId
    String description
    Set<ResponseValueDto> responseValues
}
