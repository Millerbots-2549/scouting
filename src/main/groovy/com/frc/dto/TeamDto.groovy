package com.frc.dto

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable

@CompileStatic
@EqualsAndHashCode
@Sortable(includes = ['teamId'])
class TeamDto {
    Integer teamId
    String name
    String city
    String state
    String school
    String country
}
