package com.frc.repository

import com.frc.entity.Team
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository

@CompileStatic
interface TeamRepository extends JpaRepository<Team, Integer> {

}