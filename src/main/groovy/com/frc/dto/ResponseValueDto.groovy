package com.frc.dto

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable

@CompileStatic
@EqualsAndHashCode
@Sortable(includes = ['value', 'responseValueId'])
class ResponseValueDto {
    Integer responseValueId
    String value
    Boolean isDefault
}
