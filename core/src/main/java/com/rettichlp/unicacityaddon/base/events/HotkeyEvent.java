package com.rettichlp.unicacityaddon.base.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.hotkey.HotkeyConfiguration;
import lombok.Getter;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.Event;

/**
 * @author RettichLP
 */
@Getter
public class HotkeyEvent implements Event {

    private final Key key;

    private final UnicacityAddon unicacityAddon;

    public HotkeyEvent(UnicacityAddon unicacityAddon, Key key) {
        this.unicacityAddon = unicacityAddon;
        this.key = key;
    }

    public HotkeyConfiguration hotkeyConfiguration() {
        return this.unicacityAddon.configuration().hotkey();
    }

    public boolean isRealIngame() {
        return !Laby.labyAPI().minecraft().minecraftWindow().isScreenOpened();
    }
}