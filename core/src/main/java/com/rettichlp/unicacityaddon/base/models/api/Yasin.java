package com.rettichlp.unicacityaddon.base.models.api;

import com.rettichlp.unicacityaddon.base.models.ResponseSchema;

/**
 * @author RettichLP
 */
public class Yasin extends ResponseSchema {

    private final String name;
    private final String uuid;
    private final boolean done;

    public Yasin(String name, String uuid, boolean done) {
        this.name = name;
        this.uuid = uuid;
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public boolean isDone() {
        return done;
    }
}