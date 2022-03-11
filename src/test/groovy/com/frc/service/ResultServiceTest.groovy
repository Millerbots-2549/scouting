package com.frc.service

import spock.lang.Specification

class ResultServiceTest extends Specification {

    def 'test calculateAverage'() {
        expect:
        ResultService.calculateAverage(values) == result

        where:
        values                   | result
        ['1', '2.5', '3', '2.5'] | '2.25'
        []                       | '0'
        null                     | '0'
    }
}
