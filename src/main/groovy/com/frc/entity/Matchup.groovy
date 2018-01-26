package com.frc.entity

import javax.persistence.*

@Entity
@Table(name = 'matchup')
class Matchup {

    @Id
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'start_time', nullable = false)
    Date startTime

    @Column(name = 'match_number', nullable = false)
    Integer matchNumber

    @Column(name = 'type', nullable = false, length = 45)
    String type

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    Event event

    @OneToMany(mappedBy = "matchup")
    Set<TeamMatchup> teamMatchups
}
