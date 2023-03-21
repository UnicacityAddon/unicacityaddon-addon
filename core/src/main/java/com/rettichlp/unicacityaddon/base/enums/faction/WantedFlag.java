package com.rettichlp.unicacityaddon.base.enums.faction;

import com.rettichlp.unicacityaddon.base.utils.MathUtils;

/**
 * @author RettichLP
 */
public enum WantedFlag {
    TRIED("-v", "Versuchte/r/s ", "", "x/2"),
    SUBSIDY("-b", "Beihilfe bei der/dem ", "", "x-10"),
    DRIVERS_LICENSE_WITHDRAWAL("-fsa", "", " + Führerscheinabnahme", "x"),
    WEAPONS_LICENSE_WITHDRAWAL("-wsa", "", " + Waffenscheinabnahme", "x");

    private final String flagArgument;
    private final String prependReason;
    private final String postponeReason;
    private final String wantedModification;

    WantedFlag(String flagArgument, String prependReason, String postponeReason, String wantedModification) {
        this.flagArgument = flagArgument;
        this.prependReason = prependReason;
        this.postponeReason = postponeReason;
        this.wantedModification = wantedModification;
    }

    public static WantedFlag getFlag(String string) {
        for (WantedFlag wantedFlag : WantedFlag.values()) {
            if (wantedFlag.flagArgument.equalsIgnoreCase(string))
                return wantedFlag;
        }

        return null;
    }

    public String getFlagArgument() {
        return flagArgument;
    }

    public String modifyWantedReasonString(String wantedReasonString) {
        return prependReason + wantedReasonString + postponeReason;
    }

    public int modifyWantedReasonAmount(int wantedReasonAmount) {
        return (int) new MathUtils(wantedModification.replace("x", String.valueOf(wantedReasonAmount))).evaluate();
    }
}