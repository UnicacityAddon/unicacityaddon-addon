package com.rettichlp.unicacityaddon.base.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import net.labymod.api.event.Event;

/**
 * @author RettichLP
 */
public class UnicacityAddonTickEvent implements Event {

    private final Phase phase;

    private final UnicacityAddon unicacityAddon;

    public UnicacityAddonTickEvent(UnicacityAddon unicacityAddon, Phase phase) {
        this.unicacityAddon = unicacityAddon;
        this.phase = phase;
    }

    public boolean isIngame() {
        return this.unicacityAddon.labyAPI().minecraft().isIngame();
    }

    public boolean isUnicacity() {
        return this.unicacityAddon.utils().isUnicacity();
    }

    public boolean isPhase(Phase phase) {
        return this.phase.equals(phase);
    }

    public enum Phase {

        TICK,
        TICK_5,
        MINUTE,
        SECOND,
        SECOND_3,
        SECOND_5,
        SECOND_30
    }
}