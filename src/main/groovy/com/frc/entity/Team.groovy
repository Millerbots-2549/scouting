package com.frc.entity

import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@ToString
@Table(name = 'team')
class Team {

    @Id
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'name', nullable = false, length = 100)
    String name

    @Column(name = 'city', nullable = false, length = 100)
    String city

    @Column(name = 'state', nullable = false, length = 100)
    String state

    @Column(name = 'country', nullable = false, length = 100)
    String country

    @Column(name = 'school', nullable = true, length = 100)
    String school

    @OneToMany(mappedBy = "team")
    Set<TeamMatchup> teamMatchups = new HashSet<>()

    @OneToMany(mappedBy = "team")
    Set<TeamRanking> rankings = new HashSet<>()
}
