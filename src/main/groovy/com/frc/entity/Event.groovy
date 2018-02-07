package com.frc.entity

import javax.persistence.*
import java.sql.Date

@Entity
@Table(name = 'event')
class Event {

    @Id
    @GeneratedValue
    @Column(name = 'id', nullable = false)
    Integer id

    @ManyToOne
    @JoinColumn(name = "survey_id", referencedColumnName = "id", nullable = false)
    Survey survey

    @Column(name = 'name', nullable = false, length = 100)
    String name

    @Column(name = 'city', nullable = false, length = 100)
    String city

    @Column(name = 'state', nullable = false, length = 2)
    String state

    @Column(name = 'start_date', nullable = false)
    Date startDate

    @Column(name = 'current', nullable = false)
    Boolean current

    @OneToMany(mappedBy = "event")
    Set<Matchup> matchups
}
