package com.frc.dto

import groovy.transform.Sortable

@Sortable(includes = ['value', 'id'])
class ResponseValueDto {
    Integer id
    String value
}
