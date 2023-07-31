package com.rettichlp.unicacityaddon.base.events;

import lombok.Getter;
import net.labymod.api.event.Event;

/**
 * @author RettichLP
 */
@Getter
public class BankRobStartedEvent implements Event {

    private final long delaySincePlace;

    public BankRobStartedEvent() {
        this.delaySincePlace = 0;
    }

    public BankRobStartedEvent(long placeTime) {
        this.delaySincePlace = System.currentTimeMillis() - placeTime;
    }
}