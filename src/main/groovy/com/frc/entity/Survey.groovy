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

    @Column(name = 'name', nullable = false, unique = true, length=45)
    String name

    @Enumerated(EnumType.STRING)
    @Column(name = 'type', nullable = false, length = 10)
    SurveyType type

    @Column(name = 'year', nullable = false)
    Integer year

    @Column(name = 'editable', nullable = false)
    Boolean editable

    @ManyToMany(mappedBy = "surveys")
    Set<Event> events = new HashSet<>()

    @OneToMany(mappedBy = "survey")
    Set<SurveySection> surveySections = new HashSet<>()
}
