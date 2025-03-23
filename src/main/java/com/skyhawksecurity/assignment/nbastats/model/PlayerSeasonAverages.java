package com.skyhawksecurity.assignment.nbastats.model;

import java.util.Objects;

public final class PlayerSeasonAverages {
    private int playerId;
    private int seasonId;
    private double avgPoints;
    private double avgRebounds;
    private double avgAssists;
    private double avgSteals;
    private double avgBlocks;
    private double avgFouls;
    private double avgTurnovers;
    private double avgMinutesPlayed;

    public PlayerSeasonAverages() {
    }

    public PlayerSeasonAverages(int playerId,
                                int seasonId,
                                double avgPoints,
                                double avgRebounds,
                                double avgAssists,
                                double avgSteals,
                                double avgBlocks,
                                double avgFouls,
                                double avgTurnovers,
                                double avgMinutesPlayed) {
        this.playerId = playerId;
        this.seasonId = seasonId;
        this.avgPoints = avgPoints;
        this.avgRebounds = avgRebounds;
        this.avgAssists = avgAssists;
        this.avgSteals = avgSteals;
        this.avgBlocks = avgBlocks;
        this.avgFouls = avgFouls;
        this.avgTurnovers = avgTurnovers;
        this.avgMinutesPlayed = avgMinutesPlayed;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public double getAvgPoints() {
        return avgPoints;
    }

    public double getAvgRebounds() {
        return avgRebounds;
    }

    public double getAvgAssists() {
        return avgAssists;
    }

    public double getAvgSteals() {
        return avgSteals;
    }

    public double getAvgBlocks() {
        return avgBlocks;
    }

    public double getAvgFouls() {
        return avgFouls;
    }

    public double getAvgTurnovers() {
        return avgTurnovers;
    }

    public double getAvgMinutesPlayed() {
        return avgMinutesPlayed;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public void setAvgPoints(double avgPoints) {
        this.avgPoints = avgPoints;
    }

    public void setAvgRebounds(double avgRebounds) {
        this.avgRebounds = avgRebounds;
    }

    public void setAvgAssists(double avgAssists) {
        this.avgAssists = avgAssists;
    }

    public void setAvgSteals(double avgSteals) {
        this.avgSteals = avgSteals;
    }

    public void setAvgBlocks(double avgBlocks) {
        this.avgBlocks = avgBlocks;
    }

    public void setAvgFouls(double avgFouls) {
        this.avgFouls = avgFouls;
    }

    public void setAvgTurnovers(double avgTurnovers) {
        this.avgTurnovers = avgTurnovers;
    }

    public void setAvgMinutesPlayed(double avgMinutesPlayed) {
        this.avgMinutesPlayed = avgMinutesPlayed;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PlayerSeasonAverages) obj;
        return this.playerId == that.playerId &&
                this.seasonId == that.seasonId &&
                Double.doubleToLongBits(this.avgPoints) == Double.doubleToLongBits(that.avgPoints) &&
                Double.doubleToLongBits(this.avgRebounds) == Double.doubleToLongBits(that.avgRebounds) &&
                Double.doubleToLongBits(this.avgAssists) == Double.doubleToLongBits(that.avgAssists) &&
                Double.doubleToLongBits(this.avgSteals) == Double.doubleToLongBits(that.avgSteals) &&
                Double.doubleToLongBits(this.avgBlocks) == Double.doubleToLongBits(that.avgBlocks) &&
                Double.doubleToLongBits(this.avgFouls) == Double.doubleToLongBits(that.avgFouls) &&
                Double.doubleToLongBits(this.avgTurnovers) == Double.doubleToLongBits(that.avgTurnovers) &&
                Double.doubleToLongBits(this.avgMinutesPlayed) == Double.doubleToLongBits(that.avgMinutesPlayed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, seasonId, avgPoints, avgRebounds, avgAssists, avgSteals, avgBlocks, avgFouls,
                avgTurnovers, avgMinutesPlayed);
    }

    @Override
    public String toString() {
        return "PlayerSeasonAverages[" +
                "playerId=" + playerId + ", " +
                "seasonId=" + seasonId + ", " +
                "avgPoints=" + avgPoints + ", " +
                "avgRebounds=" + avgRebounds + ", " +
                "avgAssists=" + avgAssists + ", " +
                "avgSteals=" + avgSteals + ", " +
                "avgBlocks=" + avgBlocks + ", " +
                "avgFouls=" + avgFouls + ", " +
                "avgTurnovers=" + avgTurnovers + ", " +
                "avgMinutesPlayed=" + avgMinutesPlayed + ']';
    }

}
