package com.rettichlp.UnicacityAddon.base.api.entries;

import com.rettichlp.UnicacityAddon.base.api.Syncer;

public class WantedReasonEntry {

    private final String reason;
    private final String creatorUUID;
    private final String creatorName;
    private final int points;

    public WantedReasonEntry(String reason, String creatorUUID, String creatorName, int points) {
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

    public static WantedReasonEntry getWantedReasonEntryByReason(String reason) {
        return Syncer.getWantedReaonEntryList().stream()
                .filter(wantedReasonEntry -> wantedReasonEntry.getReason().equals(reason))
                .findFirst()
                .orElse(null);
    }
}