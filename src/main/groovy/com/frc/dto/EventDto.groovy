package com.frc.dto

import java.sql.Date

class EventDto {
    Integer id
    SurveyDto survey
    String name
    String city
    String state
    Date startDate
    Boolean current
    Set<MatchupDto> matchups
}
