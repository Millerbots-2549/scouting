package com.frc.entity

import javax.persistence.*

@Entity
@Table(name = 'question')
class Question {

    @Id
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'question', nullable = false, length = 400)
    String question

    @Column(name = 'sequence', nullable = false)
    Integer sequence

    @ManyToOne
    @JoinColumn(name = "question_type_id", referencedColumnName = "id", nullable = false)
    QuestionType questionType

    @ManyToOne
    @JoinColumn(name = "survey_section_id", referencedColumnName = "id", nullable = false)
    SurveySection surveySection

    @OneToMany(mappedBy = "question")
    Set<Response> responses
}
