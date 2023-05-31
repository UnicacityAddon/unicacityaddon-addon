package com.rettichlp.unicacityaddon.base.models.api.statisticTop;

/**
 * @author RettichLP
 */
public class StatisticTopKdEntry {

    private final String name;
    private final float value;
    private final int kills;
    private final int deaths;

    public StatisticTopKdEntry(String name, float value, int kills, int deaths) {
        this.name = name;
        this.value = value;
        this.kills = kills;
        this.deaths = deaths;
    }

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }
}