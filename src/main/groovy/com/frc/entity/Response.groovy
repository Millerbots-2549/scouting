package com.frc.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = 'response')
class Response {

    @Id
    @GeneratedValue
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'response', nullable = true, length = 400)
    String response

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false)
    Question question

    @ManyToOne
    @JoinColumn(name = "team_matchup_id", referencedColumnName = "id", nullable = false)
    TeamMatchup teamMatchup


    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    Student student
}
