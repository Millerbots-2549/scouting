package com.frc.dto

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable

import java.time.Instant

@CompileStatic
@EqualsAndHashCode
@Sortable(includes = ['type', 'matchNumber', 'startTime', 'matchupId'])
class MatchupDto {
    Integer matchupId
    Instant startTime
    Integer matchNumber
    String type
    Set<TeamMatchupDto> teamMatchups
}
