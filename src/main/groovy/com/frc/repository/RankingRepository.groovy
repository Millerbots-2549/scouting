package com.frc.repository

import com.frc.entity.Event
import com.frc.entity.Ranking
import com.frc.entity.Team
import org.springframework.data.jpa.repository.JpaRepository

interface RankingRepository extends JpaRepository<Ranking, Integer> {

    Ranking findByTeamAndEvent(Team team, Event event)

}