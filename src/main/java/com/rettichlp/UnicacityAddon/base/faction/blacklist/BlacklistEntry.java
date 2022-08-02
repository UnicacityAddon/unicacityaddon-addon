package com.rettichlp.UnicacityAddon.base.faction.blacklist;

/**
 * @author RettichLP
 */
public class BlacklistEntry {

    private final String reason;
    private final int kills;
    private final int price;

    public BlacklistEntry(String blacklistReason, int blacklistKills, int blacklistPrice) {
        this.reason = blacklistReason;
        this.kills = blacklistKills;
        this.price = blacklistPrice;
    }

    public String getReason() {
        return reason;
    }

    public int getKills() {
        return kills;
    }

    public int getPrice() {
        return price;
    }
}