package com.rettichlp.unicacityaddon.api.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class EventGangwar {

    private final int attacker;
    private final int defender;
}