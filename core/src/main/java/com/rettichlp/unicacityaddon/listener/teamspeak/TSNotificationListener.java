package com.rettichlp.unicacityaddon.listener.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.events.TeamSpeakClientMoveEvent;
import com.rettichlp.unicacityaddon.base.teamspeak.models.Channel;
import com.rettichlp.unicacityaddon.base.teamspeak.models.User;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
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
    public void onTeamSpeakClientMove(TeamSpeakClientMoveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();

        Channel channel = TSNotificationListener.this.unicacityAddon.teamSpeakAPI().getServer().getChannel(e.getCid());

        User user = null;
        if (channel != null) {
            user = channel.getUser(e.getClid());
        }

        if (user != null) {
            int cid = channel.getId();
            String name = user.getDescription();

            this.unicacityAddon.services().util().debug(user.getDescription() + " -> " + channel.getName() + " (" + channel.getChannelCategory() + ")");

            if (TSNotificationListener.this.unicacityAddon.configuration().tsNotificationSupport().get() && cid == 41) {
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

                TSNotificationListener.this.unicacityAddon.soundController().playTSNotificationSupportChannelSound();
                TSNotificationListener.this.unicacityAddon.logger().info("Client joined support channel: " + name);
            } else if (TSNotificationListener.this.unicacityAddon.configuration().tsNotificationPublic().get() && cid == p.getFaction().getPublicChannelId()) {
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

                TSNotificationListener.this.unicacityAddon.soundController().playTSNotificationPublicChannelSound();
                TSNotificationListener.this.unicacityAddon.logger().info("Client joined public channel: " + name);
            }
        }
    }
}