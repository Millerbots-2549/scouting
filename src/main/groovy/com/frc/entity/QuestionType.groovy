package com.frc.entity

import javax.persistence.*

@Entity
@Table(name = 'question_type')
class QuestionType {

    @Id
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'description', nullable = false, length = 45)
    String description

    @OneToMany(mappedBy = "questionType")
    Set<Question> questions

    @OneToMany(mappedBy = "questionType")
    Set<ResponseValue> responseValues

}
