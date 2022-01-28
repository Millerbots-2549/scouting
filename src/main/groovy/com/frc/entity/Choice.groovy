package com.frc.entity

import groovy.transform.CompileStatic

import javax.persistence.*

@CompileStatic
@Entity
@Table(name = 'choice')
class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'value', nullable = false, length = 50)
    String value

    @Column(name = 'is_default', nullable = false)
    Boolean isDefault

    @ManyToOne(optional = false)
    @JoinColumn(name = "choice_group_id", referencedColumnName = "id", nullable = false)
    ChoiceGroup choiceGroup
}
