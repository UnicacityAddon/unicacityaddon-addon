package com.rettichlp.unicacityaddon.base.models.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class Revive {

    private final int currentWeekReviveAmount;
    private final int lastWeekReviveAmount;
    private final String minecraftName;
    private final String minecraftUuid;
}