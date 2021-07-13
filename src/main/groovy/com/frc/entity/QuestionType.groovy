package com.frc.entity

import groovy.transform.CompileStatic

import javax.persistence.*

@CompileStatic
@Entity
@Table(name = 'question_type')
class QuestionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'description', nullable = false, length = 45)
    String description

    @OneToMany(mappedBy = "questionType")
    Set<Question> questions = new HashSet<>()

    @OneToMany(mappedBy = "questionType")
    Set<ResponseValue> responseValues = new HashSet<>()

}
