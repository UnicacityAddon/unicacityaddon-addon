package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.config.hotkey.HotkeySetting;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ClientMoveCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Channel;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.listener.team.AdListener;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.util.math.vector.FloatVector3;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/events/AlternateScreenshotEventHandler.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class HotkeyListener {

    private final UnicacityAddon unicacityAddon;

    public HotkeyListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onKey(KeyEvent e) {
        if (Laby.references().chatAccessor().isChatOpen() || !this.unicacityAddon.isUnicacity())
            return;

        KeyEvent.State state = e.state();
        Key key = e.key();

        HotkeySetting hotkeySetting = this.unicacityAddon.configuration().hotkeySetting();
        if (state.equals(KeyEvent.State.PRESS) && hotkeySetting.enabled().get()) {
            handleHotkey(key, hotkeySetting);
        }
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isPhase(UnicacityAddonTickEvent.Phase.TICK) && Key.TAB.isPressed() && this.unicacityAddon.configuration().orderedTablist().get() && !Laby.references().chatAccessor().isChatOpen()) {
            this.unicacityAddon.tabListController().orderTabList(this.unicacityAddon.labyAPI().minecraft().getClientPacketListener().getNetworkPlayerInfos());
        }
    }

    private void handleHotkey(Key key, HotkeySetting hotkeySetting) {
        AddonPlayer p = this.unicacityAddon.player();

        if (key.equals(hotkeySetting.acceptAd().getOrDefault(Key.NONE))) {
            AdListener.handleAd("freigeben", this.unicacityAddon);
        } else if (key.equals(hotkeySetting.declineAd().getOrDefault(Key.NONE))) {
            AdListener.handleAd("blockieren", this.unicacityAddon);
        } else if (key.equals(hotkeySetting.acceptReport().getOrDefault(Key.NONE))) {
            p.sendServerMessage("/ar");
        } else if (key.equals(hotkeySetting.cancelReport().getOrDefault(Key.NONE))) {
            String farewell = this.unicacityAddon.configuration().reportMessageSetting().farewell().getOrDefault("");
            if (!farewell.isEmpty())
                p.sendServerMessage(farewell);
            p.sendServerMessage("/cr");
        } else if (key.equals(hotkeySetting.aDuty().getOrDefault(Key.NONE))) {
            p.sendServerMessage("/aduty");
        } else if (key.equals(hotkeySetting.aDutySilent().getOrDefault(Key.NONE))) {
            p.sendServerMessage("/aduty -s");
        } else if (key.equals(hotkeySetting.reinforcementFaction().getOrDefault(Key.NONE))) {
            FloatVector3 position = p.getPosition();
            p.sendServerMessage("/f Benötige Verstärkung! -> X: " + (int) position.getX() + " | Y: " + (int) position.getY() + " | Z: " + (int) position.getZ());
        } else if (key.equals(hotkeySetting.reinforcementAlliance().getOrDefault(Key.NONE))) {
            FloatVector3 position = p.getPosition();
            p.sendServerMessage("/d Benötige Verstärkung! -> X: " + (int) position.getX() + " | Y: " + (int) position.getY() + " | Z: " + (int) position.getZ());
        } else if (key.equals(hotkeySetting.publicChannel().getOrDefault(Key.NONE))) {
            if (p.getFaction().equals(Faction.NULL)) {
                p.sendErrorMessage("Du befindest dich in keiner Fraktion.");
                return;
            }

            Channel foundChannel = new Channel(p.getFaction().getPublicChannelId(), "Öffentlich", 0, 0);
            ClientMoveCommand clientMoveCommand = new ClientMoveCommand(this.unicacityAddon, foundChannel.getChannelID(), this.unicacityAddon.tsUtils().getMyClientID());

            CommandResponse commandResponse = clientMoveCommand.getResponse();
            if (!commandResponse.succeeded()) {
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