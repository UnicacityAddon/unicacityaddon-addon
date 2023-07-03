package com.rettichlp.unicacityaddon.api.houseBan;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class HouseBanReason {

    private final String reason;
    private final String creatorUUID;
    private final String creatorName;
    private final int days;
}