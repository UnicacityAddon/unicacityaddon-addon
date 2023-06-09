package com.rettichlp.unicacityaddon.listener.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.config.hotkey.HotkeySetting;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.events.HotkeyEvent;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ClientMoveCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Channel;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.Subscribe;

/**
 * @author RettichLP
 */
@UCEvent
public class TSPublicChannelListener {

    private final UnicacityAddon unicacityAddon;

    public TSPublicChannelListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onHotkey(HotkeyEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        Key key = e.key();
        HotkeySetting hotkeySetting = e.hotkeySetting();

        if (key.equals(hotkeySetting.publicChannel().getOrDefault(Key.NONE))) {
            if (p.getFaction().equals(Faction.NULL)) {
                p.sendErrorMessage("Du befindest dich in keiner Fraktion.");
                return;
            }

            Channel foundChannel = new Channel(p.getFaction().getPublicChannelId(), "Öffentlich", 0, 0);
            ClientMoveCommand clientMoveCommand = new ClientMoveCommand(this.unicacityAddon, foundChannel.getChannelID(), this.unicacityAddon.services().utilService().tsUtils().getMyClientID());

            CommandResponse commandResponse = clientMoveCommand.getResponse();
            if (commandResponse.failed()) {
                p.sendErrorMessage("Das Bewegen ist fehlgeschlagen.");
                return;
            }

            p.sendMessage(Message.getBuilder()
                    .prefix()
                    .of("Du bist in deinen").color(ColorCode.GRAY).advance().space()
                    .of("\"Öffentlich Channel\"").color(ColorCode.AQUA).advance()
                    .of(" gegangen.").color(ColorCode.GRAY).advance()
                    .createComponent());
        }
    }
}