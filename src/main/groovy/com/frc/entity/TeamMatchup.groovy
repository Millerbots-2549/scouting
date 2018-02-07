package com.frc.entity

import javax.persistence.*

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
    Set<Response> responses
}