package com.frc.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = 'response_value')
class ResponseValue {

    @Id
    @GeneratedValue
    @Column(name = 'id', nullable = false)
    Integer id

    @ManyToOne
    @JoinColumn(name = "question_type_id", referencedColumnName = "id", nullable = false)
    QuestionType questionType

    @Column(name = 'value', nullable = false, length = 45)
    String value
}
