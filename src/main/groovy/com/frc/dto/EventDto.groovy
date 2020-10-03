package com.frc.dto

import com.fasterxml.jackson.annotation.JsonAutoDetect
import groovy.transform.Sortable

import java.time.LocalDate

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY

@Sortable(includes = ['name', 'startDate', 'eventId'])
@JsonAutoDetect(fieldVisibility = ANY)
class EventDto {
    Integer eventId
    SurveyDto survey
    String name
    String city
    String state
    LocalDate startDate
    LocalDate endDate
    Set<MatchupDto> matchups
}
