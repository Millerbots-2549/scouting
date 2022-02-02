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

    @Enumerated(EnumType.STRING)
    @Column(name = 'type', nullable = false, length = 15)
    QuestionType type

    @Column(name = 'sequence', nullable = false)
    Integer sequence

    @Column(name = 'editable', nullable = false)
    Boolean editable

    @ManyToOne(optional = true)
    @JoinColumn(name = "choice_group_id", referencedColumnName = "id", nullable = true)
    ChoiceGroup choiceGroup

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "survey_section_id", referencedColumnName = "id", nullable = false)
    SurveySection surveySection

    @OneToMany(mappedBy = "question")
    Set<Response> responses = new HashSet<>()
}
