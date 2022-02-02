package com.frc

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@CompileStatic
@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@EnableAsync
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class ScoutingApplication {

    static void main(String[] args) {
        SpringApplication.run(ScoutingApplication.class, args)
    }
}