package com.frc.controller

import groovy.transform.CompileStatic
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@CompileStatic
@Controller
class PageController {

    @GetMapping(['favicon.ico'])
    @ResponseBody
    String favicon() {
        return "forward:/ico/favicon.ico"
    }

    @RequestMapping(['/', '/index', '/home'])
    String index() {
        return 'index'
    }

    @RequestMapping('/scouting')
    String scouting() {
        return 'scouting'
    }

    @Secured(['ROLE_POWER_USER', 'ROLE_ADMIN'])
    @RequestMapping('/resultsPage')
    String results() {
        return 'results'
    }

    @Secured('ROLE_ADMIN')
    @RequestMapping('/eventMaintenance')
    String events() {
        return 'events'
    }

    @Secured('ROLE_ADMIN')
    @RequestMapping('/studentMaintenance')
    String students() {
        return 'students'
    }

    @Secured('ROLE_ADMIN')
    @RequestMapping('/jobMaintenance')
    String jobMaintenance() {
        return 'jobMaintenance'
    }

}
