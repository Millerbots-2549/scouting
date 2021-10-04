package com.frc.entity

import groovy.transform.CompileStatic

import javax.persistence.*

@CompileStatic
@Entity
@Table(name = 'response_value')
class ResponseValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'id', nullable = false)
    Integer id

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_type_id", referencedColumnName = "id", nullable = false)
    QuestionType questionType

    @Column(name = 'value', nullable = false, length = 45)
    String value

    @Column(name = 'is_default', nullable = false)
    Boolean isDefault
}
