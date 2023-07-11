package com.rettichlp.unicacityaddon.listener.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.events.TeamSpeakClientMoveEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.teamspeak.models.Channel;
import com.rettichlp.unicacityaddon.base.teamspeak.models.User;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;

import java.util.Optional;
import java.util.stream.IntStream;

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
            boolean isAddonUser = name.endsWith(p.getName());

            this.unicacityAddon.utilService().debug(name + " -> " + channel.getName() + " (" + channel.getChannelCategory() + ")");

            // support waiting room
            if (this.unicacityAddon.configuration().teamspeak().support().get() && cid == 41) {
                Message.Builder messageBuilder = Message.getBuilder()
                        .prefix()
                        .of(isAddonUser ? "Du" : name).color(ColorCode.AQUA).advance().space()
                        .of(isAddonUser ? "hast das" : "hat das").color(ColorCode.GRAY).advance().space()
                        .of("Wartezimmer").color(ColorCode.AQUA).advance().space()
                        .of("betreten.").color(ColorCode.GRAY).advance().space();

                if (isAddonUser) {
                    messageBuilder
                            .of("[←]").color(ColorCode.BLUE)
                                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Betritt deinen vorherigen Channel").color(ColorCode.RED).advance().createComponent())
                                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/tsjoin id=" + e.getOldChannel().getId())
                                    .advance()
                            .createComponent();
                } else {
                    int emptySupportChannelId = getEmptySupportChannelId();
                    if (emptySupportChannelId != 0) {
                        messageBuilder
                                .of("[↑]").color(ColorCode.BLUE)
                                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Betritt einen freien Support Channel").color(ColorCode.RED).advance().createComponent())
                                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/tsjoin id=" + emptySupportChannelId)
                                        .advance().space();
                    }

                    messageBuilder
                            .of("[↓]").color(ColorCode.BLUE)
                                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Move " + name + " zu dir").color(ColorCode.RED).advance().createComponent())
                                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/movehere " + name)
                                    .advance();

                    this.unicacityAddon.soundController().playTeamSpeakSupportSound();
                    this.unicacityAddon.logger().info("Client joined support channel: " + name);
                }

                p.sendMessage(messageBuilder.createComponent());

            // public channel
            } else if (this.unicacityAddon.configuration().teamspeak().publicity().get() && cid == p.getFaction().getPublicChannelId()) {
                Message.Builder messageBuilder = Message.getBuilder()
                        .prefix()
                        .of(isAddonUser ? "Du" : name).color(ColorCode.AQUA).advance().space()
                        .of(isAddonUser ? "hast deinen" : "hat deinen").color(ColorCode.GRAY).advance().space()
                        .of("Öffentlich-Channel").color(ColorCode.AQUA).advance().space()
                        .of("betreten.").color(ColorCode.GRAY).advance().space();

                if (isAddonUser) {
                    messageBuilder
                            .of("[←]").color(ColorCode.BLUE)
                                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Betritt deinen vorherigen Channel").color(ColorCode.RED).advance().createComponent())
                                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/tsjoin id=" + e.getOldChannel().getId())
                                    .advance();
                } else {
                    messageBuilder
                            .of("[↑]").color(ColorCode.BLUE)
                                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Betritt deinen Öffentlich-Channel").color(ColorCode.RED).advance().createComponent())
                                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/tsjoin id=" + p.getFaction().getPublicChannelId())
                                    .advance().space()
                            .of("[↓]").color(ColorCode.BLUE)
                                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Move " + name + " zu dir").color(ColorCode.RED).advance().createComponent())
                                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/movehere " + name)
                                    .advance();

                    this.unicacityAddon.soundController().playTeamSpeakPublicitySound();
                    this.unicacityAddon.logger().info("Client joined public channel: " + name);
                }

                p.sendMessage(messageBuilder.createComponent());
            }
        }
    }

    private int getEmptySupportChannelId() {
        return IntStream.of(24, 25, 26)
                .filter(channelId -> Optional.ofNullable(this.unicacityAddon.teamSpeakAPI().getServer().getChannel(channelId))
                        .map(Channel::isEmpty)
                        .orElse(false))
                .findFirst()
                .orElse(0);
    }
}