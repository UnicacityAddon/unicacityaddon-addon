package com.rettichlp.unicacityaddon.base.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Broadcast {

    private final String broadcast;
    private final int id;
    private final String issuerName;
    private final String issuerUUID;
    private final long sendTime;
    private final long time;
}