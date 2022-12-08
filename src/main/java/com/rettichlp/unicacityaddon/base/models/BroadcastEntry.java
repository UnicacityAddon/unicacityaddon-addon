package com.rettichlp.unicacityaddon.base.models;

public class BroadcastEntry {

    private final String broadcast;
    private final int id;
    private final String issuerName;
    private final String issuerUUID;
    private final long sendTime;
    private final long time;

    public BroadcastEntry(String broadcast, int id, String issuerName, String issuerUUID, long sendTime, long time) {
        this.broadcast = broadcast;
        this.id = id;
        this.issuerName = issuerName;
        this.issuerUUID = issuerUUID;
        this.sendTime = sendTime;
        this.time = time;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public int getId() {
        return id;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public String getIssuerUUID() {
        return issuerUUID;
    }

    public long getSendTime() {
        return sendTime;
    }

    public long getTime() {
        return time;
    }
}