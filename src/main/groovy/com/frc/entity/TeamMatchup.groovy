package com.frc.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = 'team_matchup')
class TeamMatchup {

    @Id
    @GeneratedValue
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'alliance', nullable = false)
    String alliance

    @ManyToOne
    @JoinColumn(name = "matchup_id", referencedColumnName = "id", nullable = false)
    Matchup matchup

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    Team team

    @OneToMany(mappedBy = "teamMatchup")
    Set<Response> responses = new HashSet<>()
}