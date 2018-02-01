package com.frc.dto

class MatchupDto {
    Integer matchupId
    Date startTime
    Integer matchNumber
    String type
    Set<TeamMatchupDto> teamMatchups
}
