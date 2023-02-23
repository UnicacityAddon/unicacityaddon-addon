package com.rettichlp.unicacityaddon.base.events;

import com.rettichlp.unicacityaddon.base.models.Data;
import net.labymod.api.event.Event;

public class OfflineDataChangedEvent implements Event {

    private Data data;

    public OfflineDataChangedEvent(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }
}