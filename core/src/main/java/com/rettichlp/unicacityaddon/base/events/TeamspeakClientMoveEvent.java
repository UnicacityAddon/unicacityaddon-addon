package com.rettichlp.unicacityaddon.base.events;

import lombok.Getter;
import net.labymod.addons.teamspeak.models.Channel;
import net.labymod.addons.teamspeak.models.User;
import net.labymod.api.event.Event;

/**
 * @author RettichLP
 */
@Getter
public class TeamspeakClientMoveEvent implements Event {

    private final Channel channel;
    private final User user;

    public TeamspeakClientMoveEvent(Channel channel, User user) {
        this.channel = channel;
        this.user = user;
    }
}