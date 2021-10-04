package com.frc.repository

import com.frc.entity.Response
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository

@CompileStatic
interface ResponseRepository extends JpaRepository<Response, Integer> {

}