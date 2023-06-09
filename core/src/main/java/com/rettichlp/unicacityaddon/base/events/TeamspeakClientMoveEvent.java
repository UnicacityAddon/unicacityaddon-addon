package com.rettichlp.unicacityaddon.base.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import lombok.Getter;
import net.labymod.addons.teamspeak.models.Channel;
import net.labymod.addons.teamspeak.models.User;
import net.labymod.api.event.Event;

import java.util.concurrent.ExecutionException;

/**
 * @author RettichLP
 */
@Getter
public class TeamspeakClientMoveEvent implements Event {

    private final Channel channel;
    private String name;

    public TeamspeakClientMoveEvent(UnicacityAddon unicacityAddon, Channel channel) {
        this.channel = channel;
        this.name = unicacityAddon.player().getName();
    }

    public TeamspeakClientMoveEvent(UnicacityAddon unicacityAddon, Channel channel, User user) {
        this.channel = channel;
        try {
            this.name = user.getDescription(unicacityAddon.teamSpeakAPI()).get();
        } catch (InterruptedException | ExecutionException e) {
            this.name = "Unbekannt";
            unicacityAddon.logger().warn("TeamSpeak user description could not be obtained successfully");
        }
    }
}
