package com.rettichlp.UnicacityAddon.base.api.json;

// TODO: 20.09.2022  
public class WantedEntry {

    private final String reason;
    private final int points;
    private final String issuer;

    public WantedEntry(String reason, int points, String issuer) {
        this.reason = reason;
        this.points = points;
        this.issuer = issuer;
    }

    public String getReason() {
        return reason;
    }

    public int getPoints() {
        return points;
    }

    public String getIssuer() {
        return issuer;
    }
}