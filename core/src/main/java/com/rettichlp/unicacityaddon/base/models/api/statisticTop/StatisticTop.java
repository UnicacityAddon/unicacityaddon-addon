package com.rettichlp.unicacityaddon.base.models.api.statisticTop;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class StatisticTop {

    private final List<StatisticTopEntry> kills;
    private final List<StatisticTopKdEntry> kd;
    private final List<StatisticTopEntry> playTime;
    private final List<StatisticTopEntry> services;
    private final List<StatisticTopEntry> deaths;
    private final List<StatisticTopEntry> revives;
}