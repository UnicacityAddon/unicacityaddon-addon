package com.rettichlp.unicacityaddon.base.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import net.labymod.api.event.Event;

public class UnicacityAddonTickEvent implements Event {

    private final Phase phase;

    public UnicacityAddonTickEvent(Phase phase) {
        this.phase = phase;
    }

    public boolean isIngame() {
        return UnicacityAddon.ADDON.labyAPI().minecraft().isIngame();
    }

    public boolean isUnicacity() {
        return UnicacityAddon.isUnicacity();
    }

    public boolean isPhase(Phase phase) {
        return this.phase.equals(phase);
    }

    public enum Phase {
        TICK, TICK_5, MINUTE, SECOND, SECOND_3, SECOND_5, SECOND_30
    }
}