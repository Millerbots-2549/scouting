package com.frc.dto.blueAlliance

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

class RankingDto {
    String teamKey
    Integer rank
    Integer matchesPlayed
    Integer qualAverage
    Integer dq
}
