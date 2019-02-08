package com.frc.job

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.frc.entity.Event
import com.frc.entity.Team
import com.frc.repository.EventRepository
import com.frc.repository.TeamRepository
import org.springframework.beans.factory.annotation.Autowired


abstract class BlueAllianceClient {

    private static final String BLUE_ALLIANCE_URL = 'https://www.thebluealliance.com/api/v3'
    private static final String BLUE_ALLIANCE_AUTH_KEY = 'alXSYHeSPE3hFXTQMzYYYo4vkqra4S5RuvWXgEuPbVqFpCtxkc4paUvJr4OyHOcy'

    static final OBJECT_MAPPER = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    @Autowired
    EventRepository eventRepository
    @Autowired
    TeamRepository teamRepository

    static HttpURLConnection createConnection(Event event, String endpoint) {
        String eventKey = event.eventKey
        HttpURLConnection connection = new URL("${BLUE_ALLIANCE_URL}/event/${eventKey}/${endpoint}").openConnection() as HttpURLConnection

        // set some headers
        connection.setRequestProperty('X-TBA-Auth-Key', BLUE_ALLIANCE_AUTH_KEY)
        connection.setRequestProperty('User-Agent', 'groovy-2.4.4')
        connection.setRequestProperty('Accept', 'application/json')
        return connection
    }

    Team findTeam(String teamKey) {
        if (teamKey) {
            Integer teamId = teamKey.replaceAll('frc', '')?.trim()?.toInteger()
            if (teamId) {
                return teamRepository.findById(teamId).orElse(null)
            }
        }
        return null
    }
}
