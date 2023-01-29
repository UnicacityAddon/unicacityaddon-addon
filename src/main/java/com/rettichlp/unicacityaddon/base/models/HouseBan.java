package com.rettichlp.unicacityaddon.base.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class HouseBan {

    private final long duration;
    private final List<HouseBanReason> houseBanReasonList;
    private final long expirationTime;
    private final String name;
    private final long startTime;
    private final String uuid;
}