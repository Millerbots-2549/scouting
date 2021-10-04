package com.frc.service


import spock.lang.Specification
import spock.lang.Unroll

class ResultServiceTest extends Specification {

    @Unroll
    def 'test calculateAverage'() {
        expect:
        ResultService.calculateAverage(values) == result

        where:
        values                   | result
        ['1', '2.5', '3', '2.5'] | '2.25'
        []                       | '0'
    }
}
