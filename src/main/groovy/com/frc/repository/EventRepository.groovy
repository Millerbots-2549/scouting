package com.frc.repository

import com.frc.entity.Event
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository extends JpaRepository<Event, Integer> {
    Event findByCurrent(boolean current)

}