package com.rettichlp.UnicacityAddon.base.api.json;

// TODO: 20.09.2022
public class ErrorLogEntry {

    private final String uuid;
    private final String stacktrace;
    private final long time;

    public ErrorLogEntry(String uuid, String stacktrace, long time) {
        this.uuid = uuid;
        this.stacktrace = stacktrace;
        this.time = time;
    }

    public String getUUID() {
        return uuid;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public long getTime() {
        return time;
    }
}