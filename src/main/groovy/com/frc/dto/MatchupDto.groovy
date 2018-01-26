package com.frc.dto

class MatchupDto {
    Integer id
    Date startTime
    Integer matchNumber
    String type
    Set<TeamMatchupDto> teamMatchups
}
