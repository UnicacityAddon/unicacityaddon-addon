package com.rettichlp.unicacityaddon.base.models.api;

import com.rettichlp.unicacityaddon.base.models.ResponseSchema;

/**
 * @author RettichLP
 */
public class Revive extends ResponseSchema {

    private final int currentWeekReviveAmount;
    private final int lastWeekReviveAmount;
    private final String minecraftName;
    private final String minecraftUuid;

    public Revive(int currentWeekReviveAmount, int lastWeekReviveAmount, String minecraftName, String minecraftUuid) {
        this.currentWeekReviveAmount = currentWeekReviveAmount;
        this.lastWeekReviveAmount = lastWeekReviveAmount;
        this.minecraftName = minecraftName;
        this.minecraftUuid = minecraftUuid;
    }

    public int getCurrentWeekReviveAmount() {
        return currentWeekReviveAmount;
    }

    public int getLastWeekReviveAmount() {
        return lastWeekReviveAmount;
    }

    public String getMinecraftName() {
        return minecraftName;
    }

    public String getMinecraftUuid() {
        return minecraftUuid;
    }
}