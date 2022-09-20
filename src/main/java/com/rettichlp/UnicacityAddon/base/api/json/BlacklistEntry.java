package com.rettichlp.UnicacityAddon.base.api.json;

// TODO: 20.09.2022
public class BlacklistEntry {

    private final String reason;
    private final int price;
    private final int kills;
    private final String issuerUUID;
    private final String issuerName;

    public BlacklistEntry(String reason, int price, int kills, String issuerUUID, String issuerName) {
        this.reason = reason;
        this.price = price;
        this.kills = kills;
        this.issuerUUID = issuerUUID;
        this.issuerName = issuerName;
    }

    public String getReason() {
        return reason;
    }

    public int getPrice() {
        return price;
    }

    public int getKills() {
        return kills;
    }

    public String getIssuerUUID() {
        return issuerUUID;
    }

    public String getIssuerName() {
        return issuerName;
    }
}