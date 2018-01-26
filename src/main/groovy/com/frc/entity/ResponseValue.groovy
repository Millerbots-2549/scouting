package com.frc.entity

import javax.persistence.*

@Entity
@Table(name = 'response_value')
class ResponseValue {

    @Id
    @Column(name = 'id', nullable = false)
    Integer id

    @ManyToOne
    @JoinColumn(name = "question_type_id", referencedColumnName = "id", nullable = false)
    QuestionType questionType

    @Column(name = 'value', nullable = false, length = 45)
    String value
}
