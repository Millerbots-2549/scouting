package com.frc.entity

import groovy.transform.CompileStatic

import javax.persistence.*

@CompileStatic
@Entity
@Table(name = 'survey')
class Survey {

    @Id
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'name', nullable = false)
    String name

    @ManyToMany(mappedBy = "surveys")
    Set<Event> events = new HashSet<>()

    @OneToMany(mappedBy = "survey")
    Set<SurveySection> surveySections = new HashSet<>()

    @Transient
    SurveyType getType() {
        if (this.name.contains('Pit')) {
            return SurveyType.PIT
        } else {
            return SurveyType.MATCH
        }
    }
}
