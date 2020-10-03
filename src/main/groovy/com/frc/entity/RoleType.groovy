package com.frc.entity

enum RoleType {
    ROLE_USER, // can only enter scouting data
    ROLE_POWER_USER, // can see results page, cannot do maintenance
    ROLE_ADMIN // can do and see everything
}