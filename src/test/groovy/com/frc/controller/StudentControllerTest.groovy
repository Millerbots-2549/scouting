package com.frc.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles('local')
class StudentControllerTest extends Specification {

    @Autowired
    StudentController controller

    def 'get all students'() {
        expect:
        def results = controller.getAll()
        results
    }

}
