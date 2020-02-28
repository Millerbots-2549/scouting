package com.frc.entity

import javax.persistence.*

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
}
