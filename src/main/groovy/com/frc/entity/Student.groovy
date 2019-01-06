package com.frc.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = 'student')
class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'first_name', nullable = false)
    String firstName

    @Column(name = 'last_name', nullable = false)
    String lastName

    @Column(name = 'active', nullable = false)
    Boolean active

    @OneToMany(mappedBy = "student")
    Set<Response> responses = new HashSet<>()
}
