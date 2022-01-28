package com.frc.dto

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable

@CompileStatic
@EqualsAndHashCode
@Sortable(includes = ['value', 'choiceId'])
class ChoiceDto {
    Integer choiceId
    String value
    Boolean isDefault
}
