package com.rettichlp.unicacityaddon.base.teamspeak.objects;

import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;

import java.util.Map;

/**
 * @author Fuzzlemann
 */
public class Channel implements Comparable<Channel> {

    private final int channelID;
    private final String name;
    private final int pid;
    private final int channelOrder;

    public Channel(Map<String, String> map) {
        this.channelID = CommandResponse.parseInt(map.get("cid"));
        this.name = map.get("channel_name");
        this.pid = CommandResponse.parseInt(map.get("pid"));
        this.channelOrder = CommandResponse.parseInt(map.get("channel_order"));
    }

    public Channel(int channelID, String name, int pid, int channelOrder) {
        this.channelID = channelID;
        this.name = name;
        this.pid = pid;
        this.channelOrder = channelOrder;
    }

    public int getChannelID() {
        return channelID;
    }

    public String getName() {
        return name;
    }

    public int getPid() {
        return pid;
    }

    public int getChannelOrder() {
        return channelOrder;
    }

    @Override
    public int compareTo(Channel o) {
        return Integer.compare(channelOrder, o.channelOrder);
    }
}
