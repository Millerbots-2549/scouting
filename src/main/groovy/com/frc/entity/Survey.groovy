package com.frc.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = 'survey')
class Survey {

    @Id
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'name', nullable = false)
    String name

    @Column(name = 'default', nullable = false)
    Boolean current = Boolean.FALSE

    @ManyToMany(mappedBy = "surveys")
    Set<Event> events = new HashSet<>()

    @OneToMany(mappedBy = "survey")
    Set<SurveySection> surveySections = new HashSet<>()
}
