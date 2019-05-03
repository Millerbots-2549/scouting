package com.frc.dto

import java.time.Instant

class MatchupDto {
    Integer matchupId
    Instant startTime
    Integer matchNumber
    String type
    Set<TeamMatchupDto> teamMatchups
}
