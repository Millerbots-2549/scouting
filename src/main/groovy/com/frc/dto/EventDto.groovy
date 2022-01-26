package com.frc.dto

import com.fasterxml.jackson.annotation.JsonAutoDetect
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable

import java.time.LocalDate

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY

@CompileStatic
@EqualsAndHashCode
@Sortable(includes = ['name', 'startDate', 'eventId'])
@JsonAutoDetect(fieldVisibility = ANY)
class EventDto {
    Integer eventId
    SurveyDto survey
    String name
    String city
    String state
    String eventKey
    LocalDate startDate
    LocalDate endDate
    Set<MatchupDto> matchups
}
