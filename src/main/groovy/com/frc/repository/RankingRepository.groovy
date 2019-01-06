package com.frc.repository

import com.frc.entity.Event
import com.frc.entity.TeamRanking
import com.frc.entity.Team
import org.springframework.data.jpa.repository.JpaRepository

interface RankingRepository extends JpaRepository<TeamRanking, Integer> {

    TeamRanking findByTeamAndEvent(Team team, Event event)

}