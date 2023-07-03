package com.rettichlp.unicacityaddon.base.events;

import lombok.Getter;
import net.labymod.api.event.Event;

/**
 * @author RettichLP
 */
@Getter
public class TeamSpeakClientMoveEvent implements Event {

    private final Integer cid;
    private final Integer clid;

    public TeamSpeakClientMoveEvent(Integer clid, Integer cid) {
        this.cid = cid;
        this.clid = clid;
    }
}
