package com.rettichlp.UnicacityAddon.events.teamspeak;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.teamspeak.commands.ClientVariableCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.events.ClientMovedEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Fuzzlemann, RettichLP
 */
@UCEvent
@Mod.EventBusSubscriber
public class WaitingRoomEventHandler {

    @SubscribeEvent
    public static void onClientMoved(ClientMovedEvent e) {
        //System.out.println(0);
        UPlayer p = AbstractionLayer.getPlayer();

        if (!UnicacityAddon.isUnicacity()) return;

        int clientID = e.getClientID();
        int targetChannelID = e.getTargetChannelID();

        if (ConfigElements.getTeamspeakNotifyWaitingSupport() && targetChannelID == 41) {
            handleEnterSupportChannel(p, clientID);
            p.playSound("block.note.pling");
        } else if (ConfigElements.getTeamspeakNotifyWaitingPublic() && targetChannelID == p.getFaction().getPublicChannelId()) {
            handleEnterPublicChannel(p, clientID);
            p.playSound("block.note.pling");
        }
    }

    private static void handleEnterSupportChannel(UPlayer p, int clientID) {
        new Thread(() -> {
            ClientVariableCommand.Response response = new ClientVariableCommand(clientID).getResponse();
            String name = response.getDescription();

            p.sendMessage(Message.getBuilder()
                    .prefix()
                    .of(name).color(ColorCode.AQUA).advance().space()
                    .of("hat das").color(ColorCode.GRAY).advance().space()
                    .of("Wartezimmer").color(ColorCode.AQUA).advance().space()
                    .of("betreten.").color(ColorCode.GRAY).advance().space()
                    .of("[↓]").color(ColorCode.BLUE)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Move " + name + " zu dir").color(ColorCode.RED).advance().createComponent())
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/movehere " + name)
                        .advance()
                    .createComponent());
        }).start();
    }

    private static void handleEnterPublicChannel(UPlayer p, int clientID) {
        new Thread(() -> {
            ClientVariableCommand.Response response = new ClientVariableCommand(clientID).getResponse();
            String name = response.getDescription();

            p.sendMessage(Message.getBuilder()
                    .prefix()
                    .of(String.valueOf(name)).color(ColorCode.AQUA).advance().space()
                    .of("hat den").color(ColorCode.GRAY).advance().space()
                    .of("Öffentlich-Channel").color(ColorCode.AQUA).advance().space()
                    .of("betreten.").color(ColorCode.GRAY).advance().space()
                    .of("[↑]").color(ColorCode.BLUE)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Betritt den Öffentlich-Channel").color(ColorCode.RED).advance().createComponent())
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/tsjoin Öffentlich")
                        .advance().space()
                    .of("[↓]").color(ColorCode.BLUE)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Move " + name + " zu dir").color(ColorCode.RED).advance().createComponent())
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/movehere " + name)
                        .advance()
                    .createComponent());

            System.out.println("[DEBUG] EnterPublicChannel: " + name);
        }).start();
    }
}