package com.frc.entity

import groovy.transform.CompileStatic

import javax.persistence.*

@CompileStatic
@Entity
@Table(name = 'response')
class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'response', nullable = true, length = 400)
    String response

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false)
    Question question

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_matchup_id", referencedColumnName = "id", nullable = false)
    TeamMatchup teamMatchup


    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    Student student
}
