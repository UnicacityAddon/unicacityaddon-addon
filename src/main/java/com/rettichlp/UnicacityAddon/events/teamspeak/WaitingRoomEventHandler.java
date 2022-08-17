package com.rettichlp.UnicacityAddon.events.teamspeak;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.teamspeak.commands.ClientVariableCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.events.ClientMovedEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Fuzzlemann
 */
@UCEvent
public class WaitingRoomEventHandler {

    private static final Table<Integer, Integer, Long> COOLDOWN_TABLE = HashBasedTable.create();

    @SubscribeEvent
    public static void onClientMoved(ClientMovedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (!p.isConnected()) return;

        boolean supportNotification = ConfigElements.getTeamspeakNotifyWaitingSupport();
        boolean publicNotification = ConfigElements.getTeamspeakNotifyWaitingPublic();
        if (!supportNotification && !publicNotification) return;

        int targetChannelID = e.getTargetChannelID();
        boolean support;
        if (targetChannelID == 41) {
            if (!supportNotification) return;
            support = true;
        } else if (!p.getFaction().equals(Faction.NULL) && targetChannelID == p.getFaction().getPublicChannelId()) {
            if (!publicNotification) return;
            support = false;
        } else {
            return;
        }

        int clientID = e.getClientID();

        Map<Integer, Long> row = COOLDOWN_TABLE.row(clientID);
        Long lastTime = row.get(targetChannelID);
        if (lastTime != null && (System.currentTimeMillis() - lastTime) < TimeUnit.MINUTES.toMillis(5)) return;

        COOLDOWN_TABLE.put(clientID, targetChannelID, System.currentTimeMillis());

        new Thread(() -> {
            ClientVariableCommand.Response response = new ClientVariableCommand(clientID).getResponse();
            String name = response.getDescription();

            Message.Builder messageBuilder = Message.getBuilder()
                    .prefix()
                    .of(name).color(ColorCode.BLUE).advance()
                    .space();

            if (support) {
                messageBuilder.of("hat das Wartezimmer betreten.").color(ColorCode.GRAY).advance();
            } else {
                messageBuilder.of("hat den Öffentlich-Channel betreten.").color(ColorCode.GRAY).advance()
                        .space()
                        .of("[↑]").color(ColorCode.BLUE)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Betritt den Öffentlich-Channel").color(ColorCode.GRAY).advance().createComponent())
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/tsjoin Öffentlich")
                        .advance()
                        .space();
            }

            messageBuilder.space()
                    .of("[↓]").color(ColorCode.BLUE)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Move ").color(ColorCode.GRAY).advance().of(name).color(ColorCode.BLUE).advance().of(" zu dir").color(ColorCode.GRAY).advance().createComponent())
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/movehere " + name).advance()
                    .sendTo(p.getPlayer());

            //UnicacityAddon.MINECRAFT.addScheduledTask(() -> p.playSound(Objects.requireNonNull(SoundUtil.getSoundEvent("block.note.pling")), 1, 1));
            // TODO: 17.08.2022
        }).start();
    }
}
