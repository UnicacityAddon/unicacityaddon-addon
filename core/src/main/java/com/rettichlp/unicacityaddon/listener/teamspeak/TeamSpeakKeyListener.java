package com.rettichlp.unicacityaddon.listener.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.events.HotkeyEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.Subscribe;

/**
 * @author RettichLP
 */
@UCEvent
public class TeamSpeakKeyListener {

    private final UnicacityAddon unicacityAddon;

    public TeamSpeakKeyListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onHotkey(HotkeyEvent e) {
        Key key = e.getKey();

        if (key.equals(this.unicacityAddon.configuration().hotkey().publicChannel().get())) {
            Faction faction = this.unicacityAddon.player().getFaction();
            if (!faction.equals(Faction.NULL)) {
                int cid = faction.getPublicChannelId();
                this.unicacityAddon.teamSpeakAPI().controller().move(cid);
            }
        }
    }
}