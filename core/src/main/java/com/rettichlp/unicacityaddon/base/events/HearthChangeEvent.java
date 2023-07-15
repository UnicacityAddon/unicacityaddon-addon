package com.rettichlp.unicacityaddon.base.events;

import com.rettichlp.unicacityaddon.listener.EventRegistrationListener;
import lombok.Getter;
import net.labymod.api.event.Event;

/**
 * @author RettichLP
 */
@Getter
public class HearthChangeEvent implements Event {

    private final EventRegistrationListener.Type type;
    private final float health;

    public HearthChangeEvent(float health, EventRegistrationListener.Type type) {
        this.type = type;
        this.health = health;
    }

    public long getTime() {
        return System.currentTimeMillis();
    }
}