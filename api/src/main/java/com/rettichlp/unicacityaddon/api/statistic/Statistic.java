package com.rettichlp.unicacityaddon.api.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class Statistic {

    private final String name;
    private final String uuid;
    private final UnicacityAddon unicacityaddon;
    private final GamePlay gameplay;
    private final Faction faction;
}