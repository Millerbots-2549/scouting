package com.frc.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = 'question_type')
class QuestionType {

    @Id
    @GeneratedValue
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'description', nullable = false, length = 45)
    String description

    @OneToMany(mappedBy = "questionType")
    Set<Question> questions = new HashSet<>()

    @OneToMany(mappedBy = "questionType")
    Set<ResponseValue> responseValues = new HashSet<>()

}
