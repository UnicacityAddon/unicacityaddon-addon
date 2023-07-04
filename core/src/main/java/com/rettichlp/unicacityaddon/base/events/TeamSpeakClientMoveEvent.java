package com.rettichlp.unicacityaddon.base.events;

import com.rettichlp.unicacityaddon.base.teamspeak.models.Channel;
import lombok.Getter;
import net.labymod.api.event.Event;

/**
 * @author RettichLP
 */
@Getter
public class TeamSpeakClientMoveEvent implements Event {

    private final Integer cid;
    private final Integer clid;
    private final Channel oldChannel;

    public TeamSpeakClientMoveEvent(Integer clid, Integer cid, Channel oldChannel) {
        this.cid = cid;
        this.clid = clid;
        this.oldChannel = oldChannel;
    }
}
