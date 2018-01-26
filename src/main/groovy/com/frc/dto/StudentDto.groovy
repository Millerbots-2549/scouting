package com.frc.dto

import groovy.transform.Sortable

@Sortable(includes = ['lastName', 'firstName', 'id'])
class StudentDto {
    Integer id
    String firstName
    String lastName
}
