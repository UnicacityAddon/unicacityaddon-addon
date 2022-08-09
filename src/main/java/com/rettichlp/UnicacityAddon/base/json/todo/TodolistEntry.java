package com.rettichlp.UnicacityAddon.base.json.todo;

public class TodolistEntry {

    private int id;
    private String todo;
    private long time;
    private boolean done;

    public TodolistEntry(int id, String todo) {
        this.id = id;
        this.todo = todo;
        this.time = System.currentTimeMillis();
        this.done = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
