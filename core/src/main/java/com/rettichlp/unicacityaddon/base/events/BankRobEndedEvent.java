package com.rettichlp.unicacityaddon.base.events;

import lombok.Getter;
import net.labymod.api.event.Event;

/**
 * @author RettichLP
 */
@Getter
public class BankRobEndedEvent implements Event {

    private final long removeTime;

    public BankRobEndedEvent() {
        this.removeTime = System.currentTimeMillis();
    }
}