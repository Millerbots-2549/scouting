package com.frc.repository

import com.frc.entity.Event
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

import java.time.LocalDate

@CompileStatic
interface EventRepository extends JpaRepository<Event, Integer> {

    @Query('select e from Event e where  e.startDate <= ?1 and e.endDate >= ?1')
    Set<Event> findActiveEvents(LocalDate today)
}