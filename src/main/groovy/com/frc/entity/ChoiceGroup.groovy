package com.frc.entity

import groovy.transform.CompileStatic

import javax.persistence.*

@CompileStatic
@Entity
@Table(name = 'choice_group')
class ChoiceGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'description', nullable = false, length = 100)
    String description

    @Column(name = 'editable', nullable = false)
    Boolean editable

    @OneToMany(mappedBy = "choiceGroup")
    Set<Question> questions = new HashSet<>()

    @OneToMany(mappedBy = "choiceGroup", cascade = CascadeType.ALL)
    Set<Choice> choices = new HashSet<>()

}
