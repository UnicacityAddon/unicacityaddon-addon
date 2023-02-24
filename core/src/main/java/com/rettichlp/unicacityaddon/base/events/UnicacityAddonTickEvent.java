package com.rettichlp.unicacityaddon.base.events;

import net.labymod.api.event.Event;

public class UnicacityAddonTickEvent implements Event {

    private final Phase phase;

    public UnicacityAddonTickEvent(Phase phase) {
        this.phase = phase;
    }

    public boolean isPhase(Phase phase) {
        return this.phase.equals(phase);
    }

    public enum Phase {
        TICK, SECOND
    }
}