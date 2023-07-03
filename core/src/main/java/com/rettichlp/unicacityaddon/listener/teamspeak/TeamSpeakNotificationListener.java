package com.rettichlp.unicacityaddon.listener.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.events.TeamSpeakClientMoveEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.teamspeak.listener.ClientMovedListener;
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
public class TeamSpeakNotificationListener {

    private final UnicacityAddon unicacityAddon;

    public TeamSpeakNotificationListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onTeamSpeakClientMove(TeamSpeakClientMoveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();

        Channel channel = this.unicacityAddon.teamSpeakAPI().getServer().getChannel(e.getCid());

        User user = null;
        if (channel != null) {
            user = channel.getUser(e.getClid());
        }

        if (user != null) {
            int cid = channel.getId();
            String name = user.getDescription() != null ? user.getDescription() : "Unbekannt";

            this.unicacityAddon.utilService().debug(name + " -> " + channel.getName() + " (" + channel.getChannelCategory() + ")");

            if (this.unicacityAddon.configuration().teamspeak().support().get() && cid == 41) {
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

                this.unicacityAddon.soundController().playTeamSpeakSupportSound();
                this.unicacityAddon.logger().info("Client joined support channel: " + name);
            } else if (this.unicacityAddon.configuration().teamspeak().publicity().get() && cid == p.getFaction().getPublicChannelId()) {
                if (!name.equals(p.getName())) {
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
                } else {
                    p.sendMessage(Message.getBuilder()
                            .prefix()
                            .of("Du").color(ColorCode.AQUA).advance().space()
                            .of("hast deinen").color(ColorCode.GRAY).advance().space()
                            .of("Öffentlich-Channel").color(ColorCode.AQUA).advance().space()
                            .of("betreten.").color(ColorCode.GRAY).advance().space()
                            .of("[↑]").color(ColorCode.BLUE)
                                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                            .of("Betritt deinen vorhierigen Channel").color(ColorCode.RED).advance().space()
                                            .of("(").color(ColorCode.GRAY).advance()
                                            .of(ClientMovedListener.oldChannel.replace("» ", "")).color(ColorCode.AQUA).advance()
                                            .of(")").color(ColorCode.GRAY).advance()
                                            .createComponent())
                                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/tsjoin " + ClientMovedListener.oldChannel.replace("» ", ""))
                                    .advance()
                            .createComponent());
                }
                this.unicacityAddon.soundController().playTeamSpeakPublicitySound();
                this.unicacityAddon.logger().info("Client joined public channel: " + name);
            }
        }
    }
}