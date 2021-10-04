package com.frc.dto.view

import groovy.transform.CompileStatic
import groovy.transform.Sortable

@CompileStatic
@Sortable(includes = ['matchup'])
class ResponseResultDto {
    String response
    String matchup
    String studentName
}
