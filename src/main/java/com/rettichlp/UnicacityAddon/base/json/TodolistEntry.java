package com.rettichlp.UnicacityAddon.base.json;

public class TodolistEntry {

    private final String todo;
    private boolean done;

    public TodolistEntry(String todo) {
        this.todo = todo;
        this.done = false;
    }

    public String getTodo() {
        return todo;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
