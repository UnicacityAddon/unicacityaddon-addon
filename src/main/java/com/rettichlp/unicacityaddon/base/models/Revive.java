package com.rettichlp.unicacityaddon.base.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@AllArgsConstructor
@Getter
public class Revive {

    private int currentWeekReviveAmount;
    private int lastWeekReviveAmount;
    private String minecraftName;
    private String minecraftUuid;
}