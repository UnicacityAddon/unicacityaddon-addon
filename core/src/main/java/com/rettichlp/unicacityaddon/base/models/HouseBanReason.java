package com.rettichlp.unicacityaddon.base.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HouseBanReason {

    private final String reason;
    private final String creatorUUID;
    private final String creatorName;
    private final int days;
}