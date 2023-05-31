package com.rettichlp.unicacityaddon.base.models.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.models.ResponseSchema;

/**
 * @author RettichLP
 */
public class WantedReason extends ResponseSchema {

    private final String reason;
    private final int points;

    public WantedReason(String reason, int points) {
        this.reason = reason;
        this.points = points;
    }

    public String getReason() {
        return reason;
    }

    public int getPoints() {
        return points;
    }

    public static WantedReason getWantedReasonEntryByReason(String reason, UnicacityAddon unicacityAddon) {
        return unicacityAddon.api().getWantedReasonList().stream()
                .filter(wantedReason -> wantedReason.getReason().equals(reason))
                .findFirst()
                .orElse(null);
    }
}