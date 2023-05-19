package com.rettichlp.unicacityaddon.listener.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ClientVariableCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.events.ClientMovedEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;

/**
 * @author Fuzzlemann
 * @author RettichLP
 */
@UCEvent
public class TSNotificationListener {

    private final UnicacityAddon unicacityAddon;

    public TSNotificationListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onClientMoved(ClientMovedEvent e) {
        AddonPlayer p = this.unicacityAddon.player();

        if (!this.unicacityAddon.utils().isUnicacity())
            return;

        int clientID = e.getClientID();
        int targetChannelID = e.getTargetChannelID();

        if (this.unicacityAddon.configuration().tsNotificationSupport().get() && targetChannelID == 41) {
            handleEnterSupportChannel(p, clientID);
            this.unicacityAddon.soundController().playTSNotificationSupportChannelSound();
        } else if (this.unicacityAddon.configuration().tsNotificationPublic().get() && targetChannelID == p.getFaction().getPublicChannelId()) {
            handleEnterPublicChannel(p, clientID);
            this.unicacityAddon.soundController().playTSNotificationPublicChannelSound();
        }
    }

    private void handleEnterSupportChannel(AddonPlayer p, int clientID) {
        new Thread(() -> {
            ClientVariableCommand.Response response = new ClientVariableCommand(this.unicacityAddon, clientID).getResponse();
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

    private void handleEnterPublicChannel(AddonPlayer p, int clientID) {
        new Thread(() -> {
            ClientVariableCommand.Response response = new ClientVariableCommand(this.unicacityAddon, clientID).getResponse();
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

            this.unicacityAddon.logger().info("Client joined public channel: " + name);
        }).start();
    }
}