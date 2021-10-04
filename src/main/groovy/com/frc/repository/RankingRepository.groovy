package com.frc.repository

import com.frc.entity.Event
import com.frc.entity.Team
import com.frc.entity.TeamRanking
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository

@CompileStatic
interface RankingRepository extends JpaRepository<TeamRanking, Integer> {

    TeamRanking findByTeamAndEvent(Team team, Event event)

}