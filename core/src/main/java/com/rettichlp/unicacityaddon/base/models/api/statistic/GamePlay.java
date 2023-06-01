package com.rettichlp.unicacityaddon.base.models.api.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class GamePlay {

    private final int deaths;
    private final float kd;
    private final int kills;
    private final int playTime;
    private final int revives;
    private final int services;
}