package com.rettichlp.unicacityaddon.listener.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.events.TeamspeakClientMoveEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.addons.teamspeak.models.Channel;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;

/**
 * @author RettichLP
 */
@UCEvent
public class TSNotificationListener {

    private final UnicacityAddon unicacityAddon;

    public TSNotificationListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onTeamspeakClientMove(TeamspeakClientMoveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        Channel channel = e.getChannel();
        String name = e.getName();

        if (this.unicacityAddon.configuration().tsNotificationSupport().get() && channel.getId() == 41) {
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

            this.unicacityAddon.soundController().playTSNotificationSupportChannelSound();
            this.unicacityAddon.logger().info("Client joined support channel: " + name);
        } else if (this.unicacityAddon.configuration().tsNotificationPublic().get() && channel.getId() == p.getFaction().getPublicChannelId()) {
            p.sendMessage(Message.getBuilder()
                    .prefix()
                    .of(name).color(ColorCode.AQUA).advance().space()
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

            this.unicacityAddon.soundController().playTSNotificationPublicChannelSound();
            this.unicacityAddon.logger().info("Client joined public channel: " + name);
        }
    }
}