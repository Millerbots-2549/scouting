package com.frc.entity

import groovy.transform.CompileStatic

import javax.persistence.*
import java.time.LocalDate

@CompileStatic
@Entity
@Table(name = 'event')
class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'name', nullable = false, length = 100)
    String name

    @Column(name = 'city', nullable = false, length = 100)
    String city

    @Column(name = 'state', nullable = false, length = 2)
    String state

    @Column(name = 'start_date', nullable = false)
    LocalDate startDate

    @Column(name = 'end_date', nullable = false)
    LocalDate endDate

    @Column(name = 'event_key', nullable = false, length = 20)
    String eventKey

    @OneToMany(mappedBy = "event")
    Set<Matchup> matchups = new HashSet<>()

    @OneToMany(mappedBy = "event")
    Set<TeamRanking> rankings = new HashSet<>()

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
            name = "event_survey",
            joinColumns = [@JoinColumn(name = "event_id")],
            inverseJoinColumns = [@JoinColumn(name = "survey_id")]
    )
    Set<Survey> surveys = new HashSet<>()

    @Transient
    boolean isActive() {
        LocalDate now = LocalDate.now()
        return startDate.isBefore(now) && endDate.isAfter(now)
    }
}
