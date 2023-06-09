package com.rettichlp.unicacityaddon.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class BlacklistReason {

    private final String reason;
    private final int kills;
    private final int price;

    public static BlacklistReason getBlacklistReasonEntryByReason(String reason, UnicacityAddon unicacityAddon) {
        return unicacityAddon.api().getBlacklistReasonList().stream()
                .filter(blacklistReasonEntry -> blacklistReasonEntry.getReason().equals(reason))
                .findFirst()
                .orElse(null);
    }
}