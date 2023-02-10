package com.rettichlp.unicacityaddon.base.models;

import com.rettichlp.unicacityaddon.base.api.request.APIConverter;

public class BlacklistReason {

    private final int kills;
    private final String reason;
    private final String issuerUUID;
    private final int price;
    private final String issuerName;

    public BlacklistReason(int kills, String reason, String issuerUUID, int price, String issuerName) {
        this.kills = kills;
        this.reason = reason;
        this.issuerUUID = issuerUUID;
        this.price = price;
        this.issuerName = issuerName;
    }

    public int getKills() {
        return kills;
    }

    public String getReason() {
        return reason;
    }

    public String getIssuerUUID() {
        return issuerUUID;
    }

    public int getPrice() {
        return price;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public static BlacklistReason getBlacklistReasonEntryByReason(String reason) {
        return APIConverter.getBlacklistReasonEntryList().stream()
                .filter(blacklistReasonEntry -> blacklistReasonEntry.getReason().equals(reason))
                .findFirst()
                .orElse(null);
    }
}