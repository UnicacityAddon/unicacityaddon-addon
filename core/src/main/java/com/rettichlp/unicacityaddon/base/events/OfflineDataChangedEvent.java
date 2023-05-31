package com.rettichlp.unicacityaddon.base.events;

import com.rettichlp.unicacityaddon.base.models.file.Data;
import net.labymod.api.event.Event;

/**
 * @author RettichLP
 */
public class OfflineDataChangedEvent implements Event {

    private final Data data;

    public OfflineDataChangedEvent(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }
}