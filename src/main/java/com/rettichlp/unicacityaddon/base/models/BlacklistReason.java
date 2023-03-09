package com.rettichlp.unicacityaddon.base.models;

import com.rettichlp.unicacityaddon.base.api.Syncer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BlacklistReason {

    private final String reason;
    private final int kills;
    private final int price;

    public static BlacklistReason getBlacklistReasonEntryByReason(String reason) {
        return Syncer.BLACKLISTREASONLIST.stream()
                .filter(blacklistReasonEntry -> blacklistReasonEntry.getReason().equals(reason))
                .findFirst()
                .orElse(null);
    }
}