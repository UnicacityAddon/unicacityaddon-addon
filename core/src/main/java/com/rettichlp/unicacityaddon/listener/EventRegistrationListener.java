package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.config.UnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.config.hotkey.HotkeySetting;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
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

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/events/AlternateScreenshotEventHandler.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class EventRegistrationListener {

    public static int amountLeft = 0;

    private int slotNumber = -1;

    private final UnicacityAddon unicacityAddon;

    public EventRegistrationListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onKey(KeyEvent e) {
        if (Laby.references().chatAccessor().isChatOpen() || !this.unicacityAddon.utils().isUnicacity())
            return;

        KeyEvent.State state = e.state();
        Key key = e.key();
        UnicacityAddonConfiguration configuration = this.unicacityAddon.configuration();

        if (state.equals(KeyEvent.State.PRESS) && key.equals(Key.TAB) && configuration.orderedTablist().get()) {
            this.unicacityAddon.tabListController().orderTabList(this.unicacityAddon);
            return;
        }

        HotkeySetting hotkeySetting = configuration.hotkeySetting();
        if (state.equals(KeyEvent.State.PRESS) && hotkeySetting.enabled().get()) {
            handleHotkey(key, hotkeySetting);
        }
    }

    private void handleHotkey(Key key, HotkeySetting hotkeySetting) {
        AddonPlayer p = this.unicacityAddon.player();

        if (key.equals(hotkeySetting.acceptReport().getOrDefault(Key.NONE))) {
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
            p.sendServerMessage("/reinforcement -f");
        } else if (key.equals(hotkeySetting.reinforcementAlliance().getOrDefault(Key.NONE))) {
            p.sendServerMessage("/reinforcement -d");
        } else if (key.equals(hotkeySetting.aBuy().getOrDefault(Key.NONE))) {
            amountLeft = this.unicacityAddon.configuration().aBuyAmount().getOrDefault(5);
            slotNumber = ScreenRenderListener.lastHoveredSlotNumber;

            if (slotNumber >= 0) {
                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (amountLeft > 0) {
                            EventRegistrationListener.this.unicacityAddon.guiController().inventoryClick(EventRegistrationListener.this.unicacityAddon, slotNumber);
                            amountLeft--;
                        } else {
                            this.cancel();
                            slotNumber = -1;
                        }
                    }
                }, 0, 200);
            }
        } else if (key.equals(hotkeySetting.publicChannel().getOrDefault(Key.NONE))) {
            if (p.getFaction().equals(Faction.NULL)) {
                p.sendErrorMessage("Du befindest dich in keiner Fraktion.");
                return;
            }

            Channel foundChannel = new Channel(p.getFaction().getPublicChannelId(), "Öffentlich", 0, 0);
            ClientMoveCommand clientMoveCommand = new ClientMoveCommand(this.unicacityAddon, foundChannel.getChannelID(), this.unicacityAddon.utils().tsUtils().getMyClientID());

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