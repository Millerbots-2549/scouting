package com.frc.entity

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = 'matchup')
class Matchup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
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

    @OneToMany(mappedBy = "matchup", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<TeamMatchup> teamMatchups = new HashSet<>()
}
