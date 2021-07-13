package com.frc.entity

import groovy.transform.CompileStatic

import javax.persistence.*

@CompileStatic
@Entity
@Table(name = 'team_matchup')
class TeamMatchup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'alliance', nullable = false)
    String alliance

    @Column(name = 'response_saved', nullable = false)
    Boolean responseSaved

    @Column(name = 'alliance_order', nullable = false)
    Integer allianceOrder

    @ManyToOne(optional = false)
    @JoinColumn(name = "matchup_id", referencedColumnName = "id", nullable = false)
    Matchup matchup

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    Team team

    @OneToMany(mappedBy = "teamMatchup", cascade = CascadeType.ALL)
    Set<Response> responses = new HashSet<>()
}