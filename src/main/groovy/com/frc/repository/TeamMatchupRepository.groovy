package com.frc.repository

import com.frc.entity.TeamMatchup
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository

@CompileStatic
interface TeamMatchupRepository extends JpaRepository<TeamMatchup, Integer> {

}