package com.skyhawksecurity.assignment.nbastats.model;

import java.time.LocalDate;

public record GameRequest(int gameId,
                          int seasonId,
                          LocalDate gameDate,
                          int homeTeamId,
                          int awayTeamId) {
    public GameRequest(GameStatsRequest request) {
        this(request.gameId(), request.seasonId(), request.gameDate(), request.homeTeamId(), request.awayTeamId());
    }
}
