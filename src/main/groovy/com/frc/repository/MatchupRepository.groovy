package com.frc.repository

import com.frc.entity.Matchup
import org.springframework.data.jpa.repository.JpaRepository

interface MatchupRepository extends JpaRepository<Matchup, Integer> {

}