package com.frc.controller

import com.frc.service.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class PageController {

    @RequestMapping(['/', '/index', '/home'])
    String index() {
        return 'index'
    }

    @RequestMapping('/scouting')
    String scouting() {
        return 'scouting'
    }

    @Secured('ROLE_ADMIN')
    @RequestMapping('/resultsPage')
    String results() {
        return 'results'
    }

}
