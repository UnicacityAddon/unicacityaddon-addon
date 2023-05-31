package com.rettichlp.unicacityaddon.base.models.api.statisticTop;

import com.rettichlp.unicacityaddon.base.models.ResponseSchema;

import java.util.List;

/**
 * @author RettichLP
 */
public class StatisticTop extends ResponseSchema {

    private final List<StatisticTopEntry> kills;
    private final List<StatisticTopKdEntry> kd;
    private final List<StatisticTopEntry> playTime;
    private final List<StatisticTopEntry> services;
    private final List<StatisticTopEntry> deaths;
    private final List<StatisticTopEntry> revives;

    public StatisticTop(List<StatisticTopEntry> kills, List<StatisticTopKdEntry> kd, List<StatisticTopEntry> playTime, List<StatisticTopEntry> services, List<StatisticTopEntry> deaths, List<StatisticTopEntry> revives) {
        this.kills = kills;
        this.kd = kd;
        this.playTime = playTime;
        this.services = services;
        this.deaths = deaths;
        this.revives = revives;
    }

    public List<StatisticTopEntry> getKills() {
        return kills;
    }

    public List<StatisticTopKdEntry> getKd() {
        return kd;
    }

    public List<StatisticTopEntry> getPlayTime() {
        return playTime;
    }

    public List<StatisticTopEntry> getServices() {
        return services;
    }

    public List<StatisticTopEntry> getDeaths() {
        return deaths;
    }

    public List<StatisticTopEntry> getRevives() {
        return revives;
    }
}