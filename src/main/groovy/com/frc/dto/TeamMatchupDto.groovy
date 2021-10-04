package com.frc.dto

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable

@CompileStatic
@EqualsAndHashCode
@Sortable(includes = ['alliance', 'allianceOrder', 'team', 'teamMatchupId'])
class TeamMatchupDto {
    Integer teamMatchupId
    TeamDto team
    String alliance
    Integer allianceOrder
}