package com.frc.dto

import groovy.transform.Sortable

@Sortable(includes = ['value', 'responseValueId'])
class ResponseValueDto {
    Integer responseValueId
    String value
}
