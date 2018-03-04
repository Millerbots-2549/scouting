package com.frc.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = 'survey_section')
class SurveySection {

    @Id
    @GeneratedValue
    @Column(name = 'id', nullable = false)
    Integer id

    @ManyToOne
    @JoinColumn(name = "survey_id", referencedColumnName = "id", nullable = false)
    Survey survey

    @OneToMany(mappedBy = 'surveySection')
    Set<Question> questions = new HashSet<>()

    @Column(name = 'name', length = 100, nullable = false)
    String name

    @Column(name = 'sequence', nullable = false)
    Integer sequence
}
