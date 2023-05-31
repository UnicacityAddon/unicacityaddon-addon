package com.rettichlp.unicacityaddon.base.models.api.statistic;

/**
 * @author RettichLP
 */
public class Faction {

    private final String name;
    private final int rank;

    public Faction(String name, int rank) {
        this.name = name;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }
}