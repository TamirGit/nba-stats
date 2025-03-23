package com.skyhawksecurity.assignment.nbastats.model;

public record PlayerStatsRequest(int playerId,
                                 int points,
                                 int rebounds,
                                 int assists,
                                 int steals,
                                 int blocks,
                                 int fouls,
                                 int turnovers,
                                 float minutesPlayed) {
}
