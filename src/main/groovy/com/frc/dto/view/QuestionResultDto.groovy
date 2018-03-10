package com.frc.dto.view

import com.frc.entity.QuestionTypeValue
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable

@Sortable(includes = ['sectionSeq', 'questionSeq'])
@EqualsAndHashCode(includes = ['questionId'])
class QuestionResultDto {

    Integer questionId
    String question
    List<String> responses = []
    String summary
    QuestionTypeValue questionType
    Integer sectionSeq
    Integer questionSeq

    // if numeric then average
    // if choice, radio, or boolean then most select
    // if text then null

}
