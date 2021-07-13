package com.frc.dto.view

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

@CompileStatic
@EqualsAndHashCode(includes = ['surveyName', 'surveyId'])
class SurveyResultDto {

    String surveyName
    Integer surveyId
    List<QuestionResultDto> questions = []

}
