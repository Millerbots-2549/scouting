package com.frc.entity

import javax.persistence.*

@Entity
@Table(name = 'survey')
class Survey {

    @Id
    @Column(name = 'id', nullable = false)
    Integer id

    @OneToMany(mappedBy = "survey")
    Set<Event> events

    @OneToMany(mappedBy = "survey")
    Set<SurveySection> surveySections
}
