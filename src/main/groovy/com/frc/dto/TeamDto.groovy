package com.frc.dto

import groovy.transform.Sortable

@Sortable(includes = ['id'])
class TeamDto {
    Integer id
    String name
    String city
    String state
    String school
}
