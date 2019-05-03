package com.frc.entity

import javax.persistence.*
import java.time.LocalDate

@Entity
@Table(name = 'event')
class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    LocalDate startDate

    @Column(name = 'end_date', nullable = false)
    LocalDate endDate

    @Column(name = 'event_key', nullable = false)
    String eventKey

    @OneToMany(mappedBy = "event")
    Set<Matchup> matchups = new HashSet<>()

    @OneToMany(mappedBy = "event")
    Set<TeamRanking> rankings = new HashSet<>()

    @Transient
    boolean isActive() {
        LocalDate now = LocalDate.now()
        return startDate.isBefore(now) && endDate.isAfter(now)
    }
}
