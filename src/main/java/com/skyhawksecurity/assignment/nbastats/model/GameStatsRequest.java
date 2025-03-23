package com.skyhawksecurity.assignment.nbastats.model;

import java.time.LocalDate;
import java.util.List;

public record GameStatsRequest(int gameId,
                               int seasonId,
                               LocalDate gameDate,
                               int homeTeamId,
                               int awayTeamId,
                               List<PlayerStatsRequest> playerStatsRequests) {
    public void validate() {
        for (PlayerStatsRequest stat : this.playerStatsRequests()) {
            if (stat.fouls() > 6 || stat.minutesPlayed() > 48.0 || stat.minutesPlayed() < 0) {
                throw new IllegalArgumentException("Invalid player stat values");
            }
        }
    }
}
