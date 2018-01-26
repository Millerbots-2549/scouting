package com.frc.repository

import com.frc.entity.Response
import org.springframework.data.jpa.repository.JpaRepository

interface ResponseRepository extends JpaRepository<Response, Integer> {

}