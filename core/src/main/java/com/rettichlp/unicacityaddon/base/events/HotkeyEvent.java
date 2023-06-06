package com.rettichlp.unicacityaddon.base.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.hotkey.HotkeySetting;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.Event;

/**
 * @author RettichLP
 */
public class HotkeyEvent implements Event {

    private final Key key;

    private final UnicacityAddon unicacityAddon;

    public HotkeyEvent(UnicacityAddon unicacityAddon, Key key) {
        this.unicacityAddon = unicacityAddon;
        this.key = key;
    }

    public Key key() {
        return key;
    }

    public HotkeySetting hotkeySetting() {
        return this.unicacityAddon.configuration().hotkeySetting();
    }
}