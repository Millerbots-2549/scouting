package com.frc.dto.view

import groovy.transform.CompileStatic

@CompileStatic
class ResultDto {

    String teamName
    String teamId
    List<SurveyResultDto> surveys = []
}
