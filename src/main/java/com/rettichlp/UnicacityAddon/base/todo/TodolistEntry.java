package com.rettichlp.UnicacityAddon.base.todo;

public class TodolistEntry {

    private String todo;
    private long time;
    private boolean done;

    public TodolistEntry(String todo) {
        this.todo = todo;
        this.time = System.currentTimeMillis();
        this.done = false;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
