package com.frc.entity

import groovy.transform.CompileStatic

@CompileStatic
enum RoleType {
    ROLE_USER, // can only enter scouting data
    ROLE_POWER_USER, // can see results page, cannot do maintenance
    ROLE_ADMIN // can do and see everything
}