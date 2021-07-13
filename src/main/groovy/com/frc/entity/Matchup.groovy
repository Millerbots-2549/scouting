package com.frc.entity

import groovy.transform.CompileStatic

import javax.persistence.*
import java.time.Instant

@CompileStatic
@Entity
@Table(name = 'matchup')
class Matchup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'start_time', nullable = false)
    Instant startTime

    @Column(name = 'match_number', nullable = false)
    Integer matchNumber

    @Column(name = 'type', nullable = false, length = 45)
    String type

    @Column(name = 'blue_score', nullable = true)
    Integer blueScore

    @Column(name = 'red_score', nullable = true)
    Integer redScore

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    Event event

    @OneToMany(mappedBy = "matchup", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<TeamMatchup> teamMatchups = new HashSet<>()
}
