package com.frc.job

import com.frc.entity.Event

class BlueAllianceClient {

    private static final String BLUE_ALLIANCE_URL = 'https://www.thebluealliance.com/api/v3'
    private static final String BLUE_ALLIANCE_AUTH_KEY = 'alXSYHeSPE3hFXTQMzYYYo4vkqra4S5RuvWXgEuPbVqFpCtxkc4paUvJr4OyHOcy'

    static HttpURLConnection createConnection(Event event, String endpoint) {
        String eventKey = event.eventKey
        HttpURLConnection connection = new URL("${BLUE_ALLIANCE_URL}/event/${eventKey}/${endpoint}").openConnection() as HttpURLConnection

        // set some headers
        connection.setRequestProperty('X-TBA-Auth-Key', BLUE_ALLIANCE_AUTH_KEY)
        connection.setRequestProperty('User-Agent', 'groovy-2.4.4')
        connection.setRequestProperty('Accept', 'application/json')
        return connection
    }
}
