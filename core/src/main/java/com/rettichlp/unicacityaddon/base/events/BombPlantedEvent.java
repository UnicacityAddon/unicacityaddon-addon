package com.rettichlp.unicacityaddon.base.events;

import lombok.Getter;
import net.labymod.api.event.Event;

/**
 * @author RettichLP
 */
@Getter
public class BombPlantedEvent implements Event {

    private final long placeTime;

    public BombPlantedEvent() {
        this.placeTime = System.currentTimeMillis();
    }
}