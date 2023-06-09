package com.rettichlp.unicacityaddon.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class WantedReason {

    private final String reason;
    private final int points;

    public static WantedReason getWantedReasonEntryByReason(String reason, UnicacityAddon unicacityAddon) {
        return unicacityAddon.api().getWantedReasonList().stream()
                .filter(wantedReason -> wantedReason.getReason().equals(reason))
                .findFirst()
                .orElse(null);
    }
}