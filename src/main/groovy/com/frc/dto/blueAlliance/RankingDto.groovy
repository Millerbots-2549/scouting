package com.frc.dto.blueAlliance

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class RankingDto {
    @JsonProperty("team_key")
    String teamKey

    Integer rank

    @JsonProperty("matches_played")
    Integer matchesPlayed

    @JsonProperty("qual_average")
    Integer qualAverage

    Integer dq
}
