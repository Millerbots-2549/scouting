package com.frc.repository

import com.frc.entity.Event
import com.frc.entity.Matchup
import org.springframework.data.jpa.repository.JpaRepository

interface MatchupRepository extends JpaRepository<Matchup, Integer> {

    Matchup findByMatchNumberAndTypeAndEvent(Integer matchNumber, String Type, Event event)

}