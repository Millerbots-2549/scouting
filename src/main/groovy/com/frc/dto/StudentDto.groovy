package com.frc.dto

import groovy.transform.Sortable

@Sortable(includes = ['lastName', 'firstName', 'studentId'])
class StudentDto {
    Integer studentId
    String firstName
    String lastName
}
