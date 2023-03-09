package com.rettichlp.unicacityaddon.base.models;

import com.rettichlp.unicacityaddon.base.api.Syncer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WantedReason {

    private final String reason;
    private final String creatorUUID;
    private final String creatorName;
    private final int points;

    public static WantedReason getWantedReasonEntryByReason(String reason) {
        return Syncer.WANTEDREASONLIST.stream()
                .filter(wantedReason -> wantedReason.getReason().equals(reason))
                .findFirst()
                .orElse(null);
    }
}