package com.rettichlp.unicacityaddon.base.models.api.statistic;

/**
 * @author RettichLP
 */
public class GamePlay {

    private final int deaths;
    private final float kd;
    private final int kills;
    private final int playTime;
    private final int revives;
    private final int services;

    public GamePlay(int deaths, float kd, int kills, int playTime, int revives, int services) {
        this.deaths = deaths;
        this.kd = kd;
        this.kills = kills;
        this.playTime = playTime;
        this.revives = revives;
        this.services = services;
    }

    public int getDeaths() {
        return deaths;
    }

    public float getKd() {
        return kd;
    }

    public int getKills() {
        return kills;
    }

    public int getPlayTime() {
        return playTime;
    }

    public int getRevives() {
        return revives;
    }

    public int getServices() {
        return services;
    }
}