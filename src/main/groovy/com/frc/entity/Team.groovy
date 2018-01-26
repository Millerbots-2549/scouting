package com.frc.entity

import javax.persistence.*

@Entity
@Table(name = 'team')
class Team {

    @Id
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'name', nullable = false, length = 100)
    String name

    @Column(name = 'city', nullable = false, length = 100)
    String city

    @Column(name = 'state', nullable = false, length = 2)
    String state

    @Column(name = 'school', nullable = false, length = 100)
    String school

    @OneToMany(mappedBy = "team")
    Set<TeamMatchup> teamMatchups
}
