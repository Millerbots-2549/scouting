package com.frc.dto.view

import com.frc.entity.QuestionType
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable

@CompileStatic
@Sortable(includes = ['sectionSeq', 'questionSeq'])
@EqualsAndHashCode(includes = ['questionId'])
class QuestionResultDto {

    Integer questionId
    String question
    List<ResponseResultDto> responses = []
    String summary
    QuestionType questionType
    Integer sectionSeq
    Integer questionSeq

    // if numeric then average
    // if choice, radio, or boolean then most select
    // if text then null

}
