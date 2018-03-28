package com.frc.dto.blueAlliance

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class EventRankingDto {
    List<RankingDto> rankings
}
