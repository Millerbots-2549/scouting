package com.frc.entity

import groovy.transform.CompileStatic

import javax.persistence.*

@CompileStatic
@Entity
@Table(name = 'survey_section')
class SurveySection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'id', nullable = false)
    Integer id

    @ManyToOne(optional = false)
    @JoinColumn(name = "survey_id", referencedColumnName = "id", nullable = false)
    Survey survey

    @OneToMany(mappedBy = 'surveySection')
    Set<Question> questions = new HashSet<>()

    @Column(name = 'name', length = 100, nullable = false)
    String name

    @Column(name = 'sequence', nullable = false)
    Integer sequence
}
