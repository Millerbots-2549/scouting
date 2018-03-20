package com.frc.dto.view

import groovy.transform.Sortable

@Sortable(includes = ['matchup'])
class ResponseResultDto {
    String response
    String matchup
    String studentName
}
