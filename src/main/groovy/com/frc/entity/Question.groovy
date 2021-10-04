package com.frc.entity

import groovy.transform.CompileStatic

import javax.persistence.*

@CompileStatic
@Entity
@Table(name = 'question')
class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'question', nullable = false, length = 400)
    String question

    @Column(name = 'sequence', nullable = false)
    Integer sequence

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_type_id", referencedColumnName = "id", nullable = false)
    QuestionType questionType

    @ManyToOne(optional = false)
    @JoinColumn(name = "survey_section_id", referencedColumnName = "id", nullable = false)
    SurveySection surveySection

    @OneToMany(mappedBy = "question")
    Set<Response> responses = new HashSet<>()
}
