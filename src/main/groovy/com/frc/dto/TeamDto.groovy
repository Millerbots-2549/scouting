package com.frc.dto

import groovy.transform.Sortable

@Sortable(includes = ['teamId'])
class TeamDto {
    Integer teamId
    String name
    String city
    String state
    String school
    String country
}
