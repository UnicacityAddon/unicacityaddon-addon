package com.rettichlp.unicacityaddon.base.models;

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
}