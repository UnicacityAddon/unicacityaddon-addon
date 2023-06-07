package com.rettichlp.unicacityaddon.base.teamspeak.querycommand;

import lombok.Data;

import java.util.List;

/**
 * @author RettichLP
 */
@Data
public class Client extends Response {

    private final int id;
    private final int clientID;
    private final int clientDatabaseID;
    private final String uniqueID;
    private final String name;
    private final String loginName;
    private final String phoneticNickname;

    private final int clientType;
    private final String version;
    private final String platform;
    private final String country;

    private final int iconID;
    private final String description;
    private final double volumeModificator;
    private final String flagAvatar;
    private final String metaData;

    private final List<Integer> serverGroups;
    private final int channelGroupID;
    private final int channelID;

    private final boolean inputMuted;
    private final boolean inputHardware;
    private final boolean outputHardware;
    private final boolean microphoneMuted;
    private final boolean soundMuted;
    private final boolean outputOnlyMuted;

    private final long totalBytesDownloaded;
    private final long monthlyBytesDownloaded;
    private final long totalBytesUploaded;
    private final long monthlyBytesUploaded;

    private final int totalConnections;
    private final long firstTimeConnected;
    private final long lastTimeConnected;

    private final boolean away;
    private final String awayMessage;

    private final boolean channelCommander;
    private final boolean prioritySpeaker;
    private final boolean isTalker;
    private final boolean talking;
    private final boolean recording;

    private final int serverQueryViewPower;
    private final int talkPower;
    private final boolean requestedTalkPower;
    private final String talkPowerRequestMessage;

    private final int unreadMessages;
}
