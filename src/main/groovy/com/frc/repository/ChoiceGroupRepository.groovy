package com.frc.repository

import com.frc.entity.ChoiceGroup
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository

@CompileStatic
interface ChoiceGroupRepository extends JpaRepository<ChoiceGroup, Integer> {
}
