package com.frc.job

import com.frc.dto.blueAlliance.SimpleEventDto
import com.frc.entity.Event
import com.frc.repository.EventRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.net.ssl.HttpsURLConnection
import javax.transaction.Transactional

@Slf4j
//@CompileStatic
@Transactional
@Service
class EventCollector {

    @Autowired
    EventRepository eventRepository

    String getEventKey(final Event event) {
        if (event.eventKey) {
            return event.eventKey
        } else {
            List<SimpleEventDto> simpleEvents = collectSimpleEvents(event)
            SimpleEventDto simpleEvent = findSimpleEvent(simpleEvents, event)
            updateEvent(event, simpleEvent)
            return simpleEvent?.key
        }
    }

    private static List<SimpleEventDto> collectSimpleEvents(final Event event) {
        String url = "${BlueAllianceClient.BLUE_ALLIANCE_URL}/events/${event.startDate.year}/simple"
        HttpsURLConnection connection = new URL(url).openConnection() as HttpsURLConnection
        BlueAllianceClient.addHeaders(url, connection)
        if (connection.responseCode == 200) {
            SimpleEventDto[] simpleEvents = BlueAllianceClient.OBJECT_MAPPER.readValue(connection.inputStream, SimpleEventDto[].class)
            return simpleEvents as List
        }
        return null
    }

    private static SimpleEventDto findSimpleEvent(final List<SimpleEventDto> simpleEvents, final Event event) {
        return simpleEvents?.find { it.city == event.city && it.name == event.name }
    }

    private Event updateEvent(Event event, SimpleEventDto simpleEvent) {
        if (simpleEvent) {
            log.info("Found eventKey for ${event.name} to be ${simpleEvent.key}")
            event.eventKey = simpleEvent.key
            eventRepository.save(event)
        }
        return event
    }

}
