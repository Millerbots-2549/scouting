package com.frc.repository

import com.frc.entity.Event
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EventRepository extends JpaRepository<Event, Integer> {

    @Query('select e from Event e where  e.startDate <= ?1 and e.endDate >= ?1')
    Set<Event> findActiveEvents(Date today)
}