package com.rettichlp.UnicacityAddon.base.api.json;

// TODO: 20.09.2022  
public class HouseBanReason {

    private final String reason;
    private final int days;
    private String issuer;

    public HouseBanReason(String reason, int days, String issuer) {
        this.reason = reason;
        this.days = days;
        this.issuer = issuer;
    }

    public String getReason() {
        return reason;
    }

    public int getDays() {
        return days;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}