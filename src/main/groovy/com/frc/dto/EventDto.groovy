package com.frc.dto

import com.fasterxml.jackson.annotation.JsonAutoDetect
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.sql.Date

@JsonAutoDetect(fieldVisibility = ANY)
class EventDto {
    Integer eventId
    SurveyDto survey
    String name
    String city
    String state
    Date startDate
    Boolean current
    Set<MatchupDto> matchups
}
