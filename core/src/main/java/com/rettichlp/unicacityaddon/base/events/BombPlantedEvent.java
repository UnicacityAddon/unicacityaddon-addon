package com.rettichlp.unicacityaddon.base.events;

import lombok.Getter;
import net.labymod.api.event.Event;

/**
 * @author RettichLP
 */
@Getter
public class BombPlantedEvent implements Event {

    private final long delaySincePlace;

    public BombPlantedEvent() {
        this.delaySincePlace = 0;
    }

    public BombPlantedEvent(long placeTime) {
        this.delaySincePlace = System.currentTimeMillis() - placeTime;
    }
}