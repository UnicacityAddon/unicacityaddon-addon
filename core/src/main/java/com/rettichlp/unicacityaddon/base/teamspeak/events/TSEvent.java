package com.rettichlp.unicacityaddon.base.teamspeak.events;

import com.rettichlp.unicacityaddon.base.teamspeak.TSParser;
import net.labymod.api.event.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
 * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
 * @author Fuzzlemann
 */
public abstract class TSEvent implements Event {

    private final String input;
    protected final Map<String, String> map;

    TSEvent(String input) {
        super();
        this.input = input;
        this.map = TSParser.parse(input);
    }

    public String getInput() {
        return input;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Name {

        String value();
    }
}
