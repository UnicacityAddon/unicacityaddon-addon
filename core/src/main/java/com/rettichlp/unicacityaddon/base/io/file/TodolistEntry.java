package com.rettichlp.unicacityaddon.base.io.file;

import lombok.Getter;
import lombok.Setter;

/**
 * @author RettichLP
 */
@Getter
public class TodolistEntry {

    private final String todo;
    @Setter
    private boolean done;

    public TodolistEntry(String todo) {
        this.todo = todo;
        this.done = false;
    }
}
