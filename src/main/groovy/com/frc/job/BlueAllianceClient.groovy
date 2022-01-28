package com.frc.job

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.frc.dto.blueAlliance.SimpleEventDto
import com.frc.entity.Event
import com.frc.entity.Team
import com.frc.repository.EventRepository
import com.frc.repository.TeamRepository
import org.springframework.beans.factory.annotation.Autowired

import javax.net.ssl.HttpsURLConnection

//@CompileStatic
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

    Team findTeam(final String teamKey) {
        if (teamKey) {
            Integer teamId = teamKey.replaceAll('frc', '')?.trim()?.toInteger()
            if (teamId) {
                return teamRepository.findById(teamId).orElse(null)
            }
        }
        return null
    }

    static HttpsURLConnection createConnection(final Event event, final String endpoint) {
        String eventKey = getEventKey(event)
        if (eventKey) {
            String url = "${BLUE_ALLIANCE_URL}/event/${eventKey}/${endpoint}"
            HttpsURLConnection connection = new URL(url).openConnection() as HttpsURLConnection
            addHeaders(endpoint, connection)
            return connection
        } else {
            return null
        }
    }

    static void addHeaders(final String mapKey, final HttpsURLConnection connection) {
        connection.setRequestProperty('X-TBA-Auth-Key', BLUE_ALLIANCE_AUTH_KEY)
        connection.setRequestProperty('User-Agent', 'groovy-2.4.4')
        connection.setRequestProperty('Accept', 'application/json')
        if (LAST_MODIFIED.get(mapKey)) {
            connection.setRequestProperty('If-Modified-Since', LAST_MODIFIED.get(mapKey))
        }
    }

    static void updateLastModified(final String mapKey, final HttpsURLConnection connection) {
        String lastModified = connection.getHeaderField('Last-Modified')
        LAST_MODIFIED.put(mapKey, lastModified)
    }

    static String getEventKey(final Event event) {
        if (event.eventKey) {
            return event.eventKey
        } else {
            List<SimpleEventDto> simpleEvents = collectSimpleEvents(event)
            SimpleEventDto simpleEvent = findSimpleEvent(simpleEvents, event)
            return simpleEvent?.key
        }
    }

    static private List<SimpleEventDto> collectSimpleEvents(final Event event) {
        String url = "${BLUE_ALLIANCE_URL}/events/${event.startDate.year}/simple"
        HttpsURLConnection connection = new URL(url).openConnection() as HttpsURLConnection
        addHeaders(url, connection)
        if (connection.responseCode == 200) {
            SimpleEventDto[] simpleEvents = OBJECT_MAPPER.readValue(connection.inputStream, SimpleEventDto[].class)
            return simpleEvents as List
        }
        return null
    }

    static private SimpleEventDto findSimpleEvent(final List<SimpleEventDto> simpleEvents, final Event event) {
        return simpleEvents?.find { it.city == event.city && it.name == event.name }
    }

}
