package com.frc.dto.blueAlliance

import groovy.transform.CompileStatic

@CompileStatic
class SimpleEventDto {
    String key
    String name
    String eventCode
    Integer eventType
    String city
    String stateProv
    String country
    String startDate  //Event start date in yyyy-mm-dd format.
    String endDate  //Event end date in yyyy-mm-dd format.
    Integer year
}
