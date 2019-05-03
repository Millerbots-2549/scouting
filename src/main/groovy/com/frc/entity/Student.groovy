package com.frc.entity

import javax.persistence.*

@Entity
@Table(name = 'student')
class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'first_name', nullable = false)
    String firstName

    @Column(name = 'last_name', nullable = false)
    String lastName

    @Column(name = 'active', nullable = false)
    Boolean active

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    Team team

    @OneToMany(mappedBy = "student")
    Set<Response> responses = new HashSet<>()
}
