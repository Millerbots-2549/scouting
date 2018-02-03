package com.frc.repository

import com.frc.entity.Team
import org.springframework.data.jpa.repository.JpaRepository

interface TeamRepository extends JpaRepository<Team, Integer> {

}