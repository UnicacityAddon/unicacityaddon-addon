package com.rettichlp.unicacityaddon.base.models;

import com.rettichlp.unicacityaddon.base.api.Syncer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BlacklistReason {

    private final int kills;
    private final String reason;
    private final String issuerUUID;
    private final int price;
    private final String issuerName;

    public static BlacklistReason getBlacklistReasonEntryByReason(String reason) {
        return Syncer.getBlacklistReasonEntryList().stream()
                .filter(blacklistReasonEntry -> blacklistReasonEntry.getReason().equals(reason))
                .findFirst()
                .orElse(null);
    }
}