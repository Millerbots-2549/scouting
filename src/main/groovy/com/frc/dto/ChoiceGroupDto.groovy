package com.frc.dto

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable

@CompileStatic
@EqualsAndHashCode
@Sortable(includes = ['description', 'choiceGroupId'])
class ChoiceGroupDto {
    Integer choiceGroupId
    String description
    Set<ChoiceDto> choices
}
