package com.frc.controller

import spock.lang.Specification
import spock.lang.Unroll

class ResultControllerTest extends Specification {

    @Unroll
    def 'test calculateAverage'() {
        expect:
        ResultController.calculateAverage(values) == result

        where:
        values| result
        ['1', '2.5', '3', '2.5'] | '2.25'
        [] | '0'
    }
}
