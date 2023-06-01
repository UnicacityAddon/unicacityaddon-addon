package com.rettichlp.unicacityaddon.base.models.api.statisticTop;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class StatisticTopKdEntry {

    private final String name;
    private final float value;
    private final int kills;
    private final int deaths;
}