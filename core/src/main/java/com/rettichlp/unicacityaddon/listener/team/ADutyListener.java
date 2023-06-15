package com.rettichlp.unicacityaddon.listener.team;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.config.hotkey.HotkeyConfiguration;
import com.rettichlp.unicacityaddon.base.events.HotkeyEvent;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.Subscribe;

/**
 * @author RettichLP
 */
@UCEvent
public class ADutyListener {

    private final UnicacityAddon unicacityAddon;

    public ADutyListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onHotkey(HotkeyEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        Key key = e.getKey();
        HotkeyConfiguration hotkeyConfiguration = e.hotkeyConfiguration();

        if (key.equals(hotkeyConfiguration.aDuty().get())) {
            p.sendServerMessage("/aduty");
        } else if (key.equals(hotkeyConfiguration.aDutySilent().get())) {
            p.sendServerMessage("/aduty -s");
        }
    }
}