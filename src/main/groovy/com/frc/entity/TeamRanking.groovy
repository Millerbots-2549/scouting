package com.frc.entity

import groovy.transform.CompileStatic

import javax.persistence.*

@CompileStatic
@Entity
@Table(name = 'team_ranking')
class TeamRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'team_rank', nullable = false)
    Integer rank

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    Event event

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    Team team

}
