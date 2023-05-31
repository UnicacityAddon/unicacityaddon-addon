package com.rettichlp.unicacityaddon.base.models.api.statistic;

/**
 * @author RettichLP
 */
public class Groups {

    private final boolean CEO;
    private final boolean DEV;
    private final boolean MOD;
    private final boolean SUP;
    private final boolean BET;
    private final boolean VIP;

    public Groups(boolean CEO, boolean DEV, boolean MOD, boolean SUP, boolean BET, boolean VIP) {
        this.CEO = CEO;
        this.DEV = DEV;
        this.MOD = MOD;
        this.SUP = SUP;
        this.BET = BET;
        this.VIP = VIP;
    }

    public boolean isCEO() {
        return CEO;
    }

    public boolean isDEV() {
        return DEV;
    }

    public boolean isMOD() {
        return MOD;
    }

    public boolean isSUP() {
        return SUP;
    }

    public boolean isBET() {
        return BET;
    }

    public boolean isVIP() {
        return VIP;
    }
}