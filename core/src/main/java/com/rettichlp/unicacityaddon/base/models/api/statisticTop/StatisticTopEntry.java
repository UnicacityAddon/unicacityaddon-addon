package com.rettichlp.unicacityaddon.base.models.api.statisticTop;

/**
 * @author RettichLP
 */
public class StatisticTopEntry {

    private final String name;
    private final int value;

    public StatisticTopEntry(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}