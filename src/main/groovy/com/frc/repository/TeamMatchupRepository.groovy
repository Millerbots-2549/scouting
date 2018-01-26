package com.frc.repository

import com.frc.entity.TeamMatchup
import org.springframework.data.jpa.repository.JpaRepository

interface TeamMatchupRepository extends JpaRepository<TeamMatchup, Integer> {

}