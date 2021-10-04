package com.frc.entity

import groovy.transform.CompileStatic
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

import javax.persistence.*

@CompileStatic
@Entity
@Table(name = 'student')
class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'id', nullable = false)
    Integer id

    @Column(name = 'first_name', nullable = false, length = 45)
    String firstName

    @Column(name = 'last_name', nullable = false, length = 45)
    String lastName

    @Column(name = 'active', nullable = false)
    Boolean active

    @Column(name = 'username', nullable = false, length = 50)
    String username

    @Column(name = 'password', nullable = false, length = 500)
    String password

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    Team team

//    @OneToMany(mappedBy = "student")
//    Set<Response> responses = new HashSet<>()

    @OneToMany(mappedBy = 'student', fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    Set<StudentRole> roles = new HashSet<>()
}
