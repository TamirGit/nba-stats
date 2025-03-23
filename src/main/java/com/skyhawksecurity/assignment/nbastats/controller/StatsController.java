package com.skyhawksecurity.assignment.nbastats.controller;

import com.skyhawksecurity.assignment.nbastats.model.GameStatsRequest;
import com.skyhawksecurity.assignment.nbastats.model.PlayerSeasonAverages;
import com.skyhawksecurity.assignment.nbastats.model.TeamSeasonAverages;
import com.skyhawksecurity.assignment.nbastats.service.StatsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/stats")
public class StatsController {
    private static final Logger log = LogManager.getLogger(StatsController.class);

    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @PostMapping("/log")
    public ResponseEntity<Map<String, Object>> logPlayerStats(@RequestBody GameStatsRequest request) {
        try {
            statsService.logPlayerStats(request);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Stats logged successfully for game " + request.gameId());
            response.put("logged_stats", request.playerStatsRequests());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(response);
        } catch (Exception e) {
            log.error("e: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to log stats for game " + request.gameId()));
        }
    }

    @GetMapping("/player/{playerId}/season/{seasonId}/averages")
    public ResponseEntity<?> getPlayerSeasonAverages(@PathVariable int playerId, @PathVariable int seasonId) {
        try {
            Optional<PlayerSeasonAverages> averagesOpt = statsService.getPlayerSeasonAverages(playerId, seasonId);
            if (averagesOpt.isEmpty()) {
                return ResponseEntity.ok(Map.of(
                        "message", "No averages found for player " + playerId + " in season " + seasonId
                ));
            }
            return ResponseEntity.ok(averagesOpt.get());
        } catch (Exception e) {
            log.error("e: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error",
                            "Failed to retrieve player averages for player " + playerId + " season " + seasonId));
        }
    }

    @GetMapping("/team/{teamId}/season/{seasonId}/averages")
    public ResponseEntity<?> getTeamSeasonAverages(@PathVariable int teamId, @PathVariable int seasonId) {
        try {
            Optional<TeamSeasonAverages> statsOpt = statsService.getTeamSeasonAverages(teamId, seasonId);
            if (statsOpt.isEmpty()) {
                return ResponseEntity.ok(Map.of(
                        "message", "No stats found for team " + teamId + " in season " + seasonId));
            }
            return ResponseEntity.ok(statsOpt.get());
        } catch (Exception e) {
            log.error("e: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Failed to retrieve averages for team " + teamId + " season " + seasonId));
        }
    }
}
