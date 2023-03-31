package com.rettichlp.unicacityaddon.base.models;

import com.rettichlp.unicacityaddon.UnicacityAddon;

/**
 * @author RettichLP
 */
public class BlacklistReason {

    private final String reason;
    private final int kills;
    private final int price;

    public BlacklistReason(String reason, int kills, int price) {
        this.reason = reason;
        this.kills = kills;
        this.price = price;
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

    public static BlacklistReason getBlacklistReasonEntryByReason(String reason, UnicacityAddon unicacityAddon) {
        return unicacityAddon.api().getBlacklistReasonList().stream()
                .filter(blacklistReasonEntry -> blacklistReasonEntry.getReason().equals(reason))
                .findFirst()
                .orElse(null);
    }
}