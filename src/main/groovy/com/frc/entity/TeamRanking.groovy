package com.frc.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = 'team_ranking')
class TeamRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'team_rank', nullable = false)
    Integer rank

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    Event event

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    Team team

}
