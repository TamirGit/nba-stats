package com.skyhawksecurity.assignment.nbastats.service;

import com.skyhawksecurity.assignment.nbastats.model.*;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StatsService {
    private final Jdbi jdbi;

    @Autowired
    public StatsService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Transactional
    public void logPlayerStats(GameStatsRequest request) {
        request.validate();

        createOrUpdateGame(new GameRequest(request));
        updateStats(request);
    }

    public Optional<PlayerSeasonAverages> getPlayerSeasonAverages(int playerId, int seasonId) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                        "SELECT player_id, season_id, " +
                            "CAST(total_points AS DOUBLE PRECISION) / games_played AS avg_points, " +
                            "CAST(total_rebounds AS DOUBLE PRECISION) / games_played AS avg_rebounds, " +
                            "CAST(total_assists AS DOUBLE PRECISION) / games_played AS avg_assists, " +
                            "CAST(total_steals AS DOUBLE PRECISION) / games_played AS avg_steals, " +
                            "CAST(total_blocks AS DOUBLE PRECISION) / games_played AS avg_blocks, " +
                            "CAST(total_fouls AS DOUBLE PRECISION) / games_played AS avg_fouls, " +
                            "CAST(total_turnovers AS DOUBLE PRECISION) / games_played AS avg_turnovers, " +
                            "total_minutes_played / games_played AS avg_minutes_played " +
                            "FROM player_season_totals " +
                            "WHERE player_id = :playerId AND season_id = :seasonId")
                        .bind("playerId", playerId)
                        .bind("seasonId", seasonId)
                        .mapToBean(PlayerSeasonAverages.class)
                        .findOne()
        );
    }

    public Optional<TeamSeasonAverages> getTeamSeasonAverages(int teamId, int seasonId) {
        return jdbi.withHandle(handle -> {
            List<PlayerSeasonStats> teamSeasonStats = getTeamSeasonStats(teamId, seasonId, handle);

            if (teamSeasonStats.isEmpty()) {
                return Optional.empty();
            }

            return calculateAverages(teamSeasonStats);
        });
    }

    private static List<PlayerSeasonStats> getTeamSeasonStats(int teamId, int seasonId, Handle handle) {
        return handle.createQuery(
                "SELECT pst.* " +
                    "FROM player_season_totals pst " +
                    "JOIN players p ON pst.player_id = p.player_id " +
                    "WHERE p.team_id = :teamId AND pst.season_id = :seasonId")
                .bind("teamId", teamId)
                .bind("seasonId", seasonId)
                .mapToBean(PlayerSeasonStats.class)
                .list();
    }

    private static Optional<TeamSeasonAverages> calculateAverages(List<PlayerSeasonStats> playerStats) {
        double totalAvgPoints = 0, totalAvgRebounds = 0, totalAvgAssists = 0, totalAvgSteals = 0,
                totalAvgBlocks = 0, totalAvgFouls = 0, totalAvgTurnovers = 0, totalAvgMinutes = 0;

        for (PlayerSeasonStats stats : playerStats) {
            totalAvgPoints += stats.getTotalPoints() / (float) stats.getGamesPlayed();
            totalAvgRebounds += stats.getTotalRebounds() / (float) stats.getGamesPlayed();
            totalAvgAssists += stats.getTotalAssists() / (float) stats.getGamesPlayed();
            totalAvgSteals += stats.getTotalSteals() / (float) stats.getGamesPlayed();
            totalAvgBlocks += stats.getTotalBlocks() / (float) stats.getGamesPlayed();
            totalAvgFouls += stats.getTotalFouls() / (float) stats.getGamesPlayed();
            totalAvgTurnovers += stats.getTotalTurnovers() / (float) stats.getGamesPlayed();
            totalAvgMinutes += stats.getTotalMinutesPlayed() / stats.getGamesPlayed();
        }

        int playerCount = playerStats.size();
        return Optional.of(new TeamSeasonAverages(
                totalAvgPoints / playerCount,
                totalAvgRebounds / playerCount,
                totalAvgAssists / playerCount,
                totalAvgSteals / playerCount,
                totalAvgBlocks / playerCount,
                totalAvgFouls / playerCount,
                totalAvgTurnovers / playerCount,
                totalAvgMinutes / playerCount
        ));
    }

    private void createOrUpdateGame(GameRequest request) {
        jdbi.withHandle(handle ->
                handle.createUpdate(
                        "INSERT INTO games (game_id, season_id, game_date, home_team_id, away_team_id) " +
                            "VALUES (:gameId, :seasonId, :gameDate, :homeTeamId, :awayTeamId) " +
                            "ON CONFLICT (game_id) DO NOTHING")
                        .bind("gameId", request.gameId())
                        .bind("seasonId", request.seasonId())
                        .bind("gameDate", request.gameDate())
                        .bind("homeTeamId", request.homeTeamId())
                        .bind("awayTeamId", request.awayTeamId())
                        .execute()
        );
    }

    private void updateStats(GameStatsRequest request) {
        for (PlayerStatsRequest stats : request.playerStatsRequests()) {
            insertPlayerStats(request, stats);
            updateSeasonTotals(stats.playerId(), request.seasonId());
        }
    }

    private void insertPlayerStats(GameStatsRequest request, PlayerStatsRequest playerStats) {
        jdbi.withHandle(handle ->
                handle.createUpdate(
                        "INSERT INTO player_game_stats (player_id, game_id, points, rebounds, assists, steals, blocks, fouls, turnovers, minutes_played) " +
                            "VALUES (:playerId, :gameId, :points, :rebounds, :assists, :steals, :blocks, :fouls, :turnovers, :minutesPlayed) " +
                            "ON CONFLICT (player_id, game_id) DO NOTHING")
                        .bind("gameId", request.gameId())
                        .bind("playerId", playerStats.playerId())
                        .bind("gameDate", request.gameDate())
                        .bind("points", playerStats.points())
                        .bind("rebounds", playerStats.rebounds())
                        .bind("assists", playerStats.assists())
                        .bind("steals", playerStats.steals())
                        .bind("blocks", playerStats.blocks())
                        .bind("fouls", playerStats.fouls())
                        .bind("turnovers", playerStats.turnovers())
                        .bind("minutesPlayed", playerStats.minutesPlayed())
                        .execute()
        );
    }

    private void updateSeasonTotals(int playerId, int seasonId) {
        jdbi.withHandle(handle -> {
            Map<String, Object> totals = handle.createQuery(
                    "SELECT COUNT(*) as games_played, " +
                        "SUM(points) as total_points, SUM(rebounds) as total_rebounds, " +
                        "SUM(assists) as total_assists, SUM(steals) as total_steals, " +
                        "SUM(blocks) as total_blocks, SUM(fouls) as total_fouls, " +
                        "SUM(turnovers) as total_turnovers, SUM(minutes_played) as total_minutes_played " +
                        "FROM player_game_stats pgs " +
                        "JOIN games g ON pgs.game_id = g.game_id " +
                        "WHERE pgs.player_id = :playerId AND g.season_id = :seasonId")
                    .bind("playerId", playerId)
                    .bind("seasonId", seasonId)
                    .mapToMap()
                    .one();

            handle.createUpdate(
                    "INSERT INTO player_season_totals (player_id, season_id, games_played, total_points, total_rebounds, total_assists, total_steals, total_blocks, total_fouls, total_turnovers, total_minutes_played) " +
                        "VALUES (:playerId, :seasonId, :games_played, :total_points, :total_rebounds, :total_assists, :total_steals, :total_blocks, :total_fouls, :total_turnovers, :total_minutes_played) " +
                        "ON CONFLICT (player_id, season_id) DO UPDATE SET " +
                        "games_played = EXCLUDED.games_played, total_points = EXCLUDED.total_points, total_rebounds = EXCLUDED.total_rebounds, " +
                        "total_assists = EXCLUDED.total_assists, total_steals = EXCLUDED.total_steals, total_blocks = EXCLUDED.total_blocks, " +
                        "total_fouls = EXCLUDED.total_fouls, total_turnovers = EXCLUDED.total_turnovers, total_minutes_played = EXCLUDED.total_minutes_played")
                    .bindMap(totals)
                    .bind("playerId", playerId)
                    .bind("seasonId", seasonId)
                    .execute();
            return null;
        });
    }
}
