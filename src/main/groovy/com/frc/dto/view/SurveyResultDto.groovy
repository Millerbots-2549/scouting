package com.frc.dto.view

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ['surveyName', 'surveyId'])
class SurveyResultDto {

    String surveyName
    Integer surveyId
    List<QuestionResultDto> questions = []

}
