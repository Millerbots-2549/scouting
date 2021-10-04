package com.frc.dto

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable

@CompileStatic
@EqualsAndHashCode
@Sortable(includes = ['enabled', 'lastName', 'firstName', 'studentId'])
class StudentDto {
    Integer studentId
    String firstName
    String lastName
    String username
    String password
    Boolean enabled
    Boolean roleUser
    Boolean rolePowerUser
    Boolean roleAdmin
}
