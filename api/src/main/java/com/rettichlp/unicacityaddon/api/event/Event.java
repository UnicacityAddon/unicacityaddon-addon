package com.rettichlp.unicacityaddon.api.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class Event {

    private final long bankRob;
    private final long bomb;
    private final EventGangwar gangwar;
}