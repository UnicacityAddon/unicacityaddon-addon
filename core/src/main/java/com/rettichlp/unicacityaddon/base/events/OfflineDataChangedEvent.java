package com.rettichlp.unicacityaddon.base.events;

import com.rettichlp.unicacityaddon.base.io.file.Data;
import lombok.Getter;
import net.labymod.api.event.Event;

/**
 * @author RettichLP
 */
@Getter
public class OfflineDataChangedEvent implements Event {

    private final Data data;

    public OfflineDataChangedEvent(Data data) {
        this.data = data;
    }
}