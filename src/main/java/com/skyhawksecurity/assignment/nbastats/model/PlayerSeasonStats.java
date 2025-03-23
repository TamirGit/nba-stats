package com.skyhawksecurity.assignment.nbastats.model;

import java.util.Objects;

public final class PlayerSeasonStats {
    private int playerId;
    private int seasonId;
    private int gamesPlayed;
    private int totalPoints;
    private int totalRebounds;
    private int totalAssists;
    private int totalSteals;
    private int totalBlocks;
    private int totalFouls;
    private int totalTurnovers;
    private double totalMinutesPlayed;

    public PlayerSeasonStats() {
    }

    public PlayerSeasonStats(
            int playerId,
            int seasonId,
            int gamesPlayed,
            int totalPoints,
            int totalRebounds,
            int totalAssists,
            int totalSteals,
            int totalBlocks,
            int totalFouls,
            int totalTurnovers,
            double totalMinutesPlayed) {
        this.playerId = playerId;
        this.seasonId = seasonId;
        this.gamesPlayed = gamesPlayed;
        this.totalPoints = totalPoints;
        this.totalRebounds = totalRebounds;
        this.totalAssists = totalAssists;
        this.totalSteals = totalSteals;
        this.totalBlocks = totalBlocks;
        this.totalFouls = totalFouls;
        this.totalTurnovers = totalTurnovers;
        this.totalMinutesPlayed = totalMinutesPlayed;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getTotalRebounds() {
        return totalRebounds;
    }

    public int getTotalAssists() {
        return totalAssists;
    }

    public int getTotalSteals() {
        return totalSteals;
    }

    public int getTotalBlocks() {
        return totalBlocks;
    }

    public int getTotalFouls() {
        return totalFouls;
    }

    public int getTotalTurnovers() {
        return totalTurnovers;
    }

    public double getTotalMinutesPlayed() {
        return totalMinutesPlayed;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setTotalRebounds(int totalRebounds) {
        this.totalRebounds = totalRebounds;
    }

    public void setTotalAssists(int totalAssists) {
        this.totalAssists = totalAssists;
    }

    public void setTotalSteals(int totalSteals) {
        this.totalSteals = totalSteals;
    }

    public void setTotalBlocks(int totalBlocks) {
        this.totalBlocks = totalBlocks;
    }

    public void setTotalFouls(int totalFouls) {
        this.totalFouls = totalFouls;
    }

    public void setTotalTurnovers(int totalTurnovers) {
        this.totalTurnovers = totalTurnovers;
    }

    public void setTotalMinutesPlayed(double totalMinutesPlayed) {
        this.totalMinutesPlayed = totalMinutesPlayed;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PlayerSeasonStats) obj;
        return this.playerId == that.playerId &&
                this.seasonId == that.seasonId &&
                this.gamesPlayed == that.gamesPlayed &&
                this.totalPoints == that.totalPoints &&
                this.totalRebounds == that.totalRebounds &&
                this.totalAssists == that.totalAssists &&
                this.totalSteals == that.totalSteals &&
                this.totalBlocks == that.totalBlocks &&
                this.totalFouls == that.totalFouls &&
                this.totalTurnovers == that.totalTurnovers &&
                Double.doubleToLongBits(this.totalMinutesPlayed) == Double.doubleToLongBits(that.totalMinutesPlayed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, seasonId, gamesPlayed, totalPoints, totalRebounds, totalAssists, totalSteals,
                totalBlocks, totalFouls, totalTurnovers, totalMinutesPlayed);
    }

    @Override
    public String toString() {
        return "PlayerSeasonStats[" +
                "playerId=" + playerId + ", " +
                "seasonId=" + seasonId + ", " +
                "gamesPlayed=" + gamesPlayed + ", " +
                "totalPoints=" + totalPoints + ", " +
                "totalRebounds=" + totalRebounds + ", " +
                "totalAssists=" + totalAssists + ", " +
                "totalSteals=" + totalSteals + ", " +
                "totalBlocks=" + totalBlocks + ", " +
                "totalFouls=" + totalFouls + ", " +
                "totalTurnovers=" + totalTurnovers + ", " +
                "totalMinutesPlayed=" + totalMinutesPlayed + ']';
    }

}
