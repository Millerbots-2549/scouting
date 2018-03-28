package com.frc.entity

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table
import java.sql.Date

@Entity
@Table(name = 'event')
class Event {

    @Id
    @GeneratedValue
    @Column(name = 'id', nullable = false)
    Integer id

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
            name = "event_survey",
            joinColumns = [@JoinColumn(name = "event_id")],
            inverseJoinColumns = [@JoinColumn(name = "survey_id")]
    )
    Set<Survey> surveys = new HashSet<>()

    @Column(name = 'name', nullable = false, length = 100)
    String name

    @Column(name = 'city', nullable = false, length = 100)
    String city

    @Column(name = 'state', nullable = false, length = 2)
    String state

    @Column(name = 'start_date', nullable = false)
    Date startDate

    @Column(name = 'current', nullable = false)
    Boolean current

    @Column(name = 'active', nullable = false)
    Boolean active

    @Column(name = 'event_key', nullable = false)
    String eventKey

    @OneToMany(mappedBy = "event")
    Set<Matchup> matchups = new HashSet<>()

    @OneToMany(mappedBy = "event")
    Set<Ranking> rankings = new HashSet<>()
}
