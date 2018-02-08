package com.frc.entity

import javax.persistence.*

@Entity
@Table(name = 'student')
class Student {

    @Id
    @GeneratedValue
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'first_name', nullable = false)
    String firstName

    @Column(name = 'last_name', nullable = false)
    String lastName

    @Column(name = 'active', nullable = false)
    Boolean active

    @OneToMany(mappedBy = "student")
    Set<Response> responses
}