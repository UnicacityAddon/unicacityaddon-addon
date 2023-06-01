package com.rettichlp.unicacityaddon.base.models.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class Broadcast {

    private final String broadcast;
    private final int id;
    private final String issuerName;
    private final String issuerUUID;
    private final long sendTime;
    private final long time;
}