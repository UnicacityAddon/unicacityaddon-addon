package com.rettichlp.unicacityaddon.api.houseBan;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class HouseBan {

    private final long duration;
    private final List<HouseBanReason> houseBanReasonList;
    private final long expirationTime;
    private final String name;
    private final long startTime;
    private final String uuid;
}