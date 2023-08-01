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

    /**
     * Quote: "Nein ich nutze keinen Cheat" und "Nein, der Draht liegt nicht auf der Bombe" - <a href="https://i.imgur.com/zbanzRM.mp4">Gelegenheitsdieb</a>, 08.06.2023
     */
    public TeamSpeakClientMoveEvent(Integer clid, Integer cid, Channel oldChannel) {
        this.cid = cid;
        this.clid = clid;
        this.oldChannel = oldChannel;
    }
}
