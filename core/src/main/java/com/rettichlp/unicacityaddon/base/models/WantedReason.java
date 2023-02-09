package com.rettichlp.unicacityaddon.base.models;

import com.rettichlp.unicacityaddon.base.api.Syncer;

public class WantedReason {

    private final String reason;
    private final String creatorUUID;
    private final String creatorName;
    private final int points;

    public WantedReason(String reason, String creatorUUID, String creatorName, int points) {
        this.reason = reason;
        this.creatorUUID = creatorUUID;
        this.creatorName = creatorName;
        this.points = points;
    }

    public String getReason() {
        return reason;
    }

    public String getCreatorUUID() {
        return creatorUUID;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public int getPoints() {
        return points;
    }

    public static WantedReason getWantedReasonEntryByReason(String reason) {
        return Syncer.getWantedReasonEntryList().stream()
                .filter(wantedReason -> wantedReason.getReason().equals(reason))
                .findFirst()
                .orElse(null);
    }
}