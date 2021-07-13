package com.frc.job

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.frc.entity.Event
import com.frc.entity.Team
import com.frc.repository.EventRepository
import com.frc.repository.TeamRepository
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired

import javax.net.ssl.HttpsURLConnection

@CompileStatic
abstract class BlueAllianceClient {

    static final String BLUE_ALLIANCE_URL = 'https://www.thebluealliance.com/api/v3'
    static final String BLUE_ALLIANCE_AUTH_KEY = 'alXSYHeSPE3hFXTQMzYYYo4vkqra4S5RuvWXgEuPbVqFpCtxkc4paUvJr4OyHOcy'
    static boolean ENABLED = false

    static final OBJECT_MAPPER = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    static final Map<String, String> LAST_MODIFIED = [:]

    @Autowired
    EventRepository eventRepository
    @Autowired
    TeamRepository teamRepository
    @Autowired
    EventCollector eventCollector

    HttpURLConnection createConnection(final Event event, final String endpoint) {
        String eventKey = eventCollector.getEventKey(event)
        if (eventKey) {
            String url = "${BLUE_ALLIANCE_URL}/event/${eventKey}/${endpoint}"
            HttpsURLConnection connection = new URL(url).openConnection() as HttpsURLConnection
            addHeaders(connection)
            addLastModifiedHeader(endpoint, connection)
            return connection
        } else {
            return null
        }
    }

    static void addHeaders(final HttpsURLConnection connection) {
        connection.setRequestProperty('X-TBA-Auth-Key', BLUE_ALLIANCE_AUTH_KEY)
        connection.setRequestProperty('User-Agent', 'groovy-2.4.4')
        connection.setRequestProperty('Accept', 'application/json')
    }

    private static void addLastModifiedHeader(String endpoint, HttpsURLConnection connection) {
        if (LAST_MODIFIED.get(endpoint)) {
            connection.setRequestProperty('If-Modified-Since', LAST_MODIFIED.get(endpoint))
        }

        if (connection.responseCode == 200) {
            String lastModified = connection.getHeaderField('Last-Modified')
            LAST_MODIFIED.put(endpoint, lastModified)
        }
    }

    Team findTeam(final String teamKey) {
        if (teamKey) {
            Integer teamId = teamKey.replaceAll('frc', '')?.trim()?.toInteger()
            if (teamId) {
                return teamRepository.findById(teamId).orElse(null)
            }
        }
        return null
    }

}
