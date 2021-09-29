package com.frc.controller

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@Slf4j
@CompileStatic
@RestControllerAdvice
class ErrorController {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String exception(final Throwable throwable, final Model model) {
        log.error("Something bad happened", throwable)
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error")
        model.addAttribute("errorMessage", errorMessage)
        return "error"
    }

}
