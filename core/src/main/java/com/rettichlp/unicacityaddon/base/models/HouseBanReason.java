package com.rettichlp.unicacityaddon.base.models;

public class HouseBanReason {

    private final String reason;
    private final String creatorUUID;
    private final String creatorName;
    private final int days;

    public HouseBanReason(String reason, String creatorUUID, String creatorName, int days) {
        this.reason = reason;
        this.creatorUUID = creatorUUID;
        this.creatorName = creatorName;
        this.days = days;
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

    public int getDays() {
        return days;
    }
}