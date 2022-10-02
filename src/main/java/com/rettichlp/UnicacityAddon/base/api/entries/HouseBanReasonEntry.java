package com.rettichlp.UnicacityAddon.base.api.entries;

import com.rettichlp.UnicacityAddon.base.api.Syncer;

public class HouseBanReasonEntry {

    private final String reason;
    private final String creatorUUID;
    private final String creatorName;
    private final int days;

    public HouseBanReasonEntry(String reason, String creatorUUID, String creatorName, int days) {
        this.reason = reason;
        this.creatorUUID = creatorUUID;
        this.creatorName = creatorName;
        this.days = days;
    }

    public String getReason() {
        return reason;
    }

    public String getTabReason() {
        return reason.replace(" ", "-");
    }

    public int getDays() {
        return days;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getCreatorUUID() {
        return creatorUUID;
    }

    public static HouseBanReasonEntry getHouseBanReasonEntryByTabReason(String tabReason) {
        return Syncer.getHouseBanReasonEntryList().stream()
                .filter(houseBanReasonEntry -> houseBanReasonEntry.getTabReason().equals(tabReason))
                .findFirst()
                .orElse(null);
    }
}