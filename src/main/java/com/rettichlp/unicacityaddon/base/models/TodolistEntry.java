package com.rettichlp.unicacityaddon.base.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TodolistEntry {

    private final String todo;
    private boolean done;

    public void setDone(boolean done) {
        this.done = done;
    }
}
