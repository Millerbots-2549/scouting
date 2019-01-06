package com.frc.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = 'question')
class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
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
    Set<Response> responses = new HashSet<>()
}
