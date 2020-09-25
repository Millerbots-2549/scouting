package com.frc.entity

import javax.persistence.*

@Entity
@Table(name = 'student_role')
class StudentRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'id', nullable = false)
    Integer id

    @Enumerated(EnumType.STRING)
    @Column(name = 'role', nullable = false, length = 50)
    RoleType role

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    Student student
}
