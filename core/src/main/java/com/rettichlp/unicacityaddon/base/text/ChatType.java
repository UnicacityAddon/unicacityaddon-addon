package com.rettichlp.unicacityaddon.base.text;

/**
 * @author RettichLP
 */
public enum ChatType {

    ADMIN("Serverweit", "o"),
    CHAT("Chat", ""),
    DCHAT("Bündnis", "d"),
    FCHAT("Fraktion", "f"),
    SFCHAT("Subfraktion", "sf"),
    LEADER("Leader", "l"),
    GRCHAT("Gruppierung", "gr"),
    SCREAM("Schreien", "s"),
    WHISPER("Flüstern", "w");

    private final String displayName;
    private final String chatType;

    ChatType(String displayName, String chatType) {
        this.displayName = displayName;
        this.chatType = chatType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getChatType() {
        return chatType;
    }

    public String getChatCommand() {
        return "/" + getChatType();
    }

    public static ChatType getChatTypeByDisplayName(String s) {
        for (ChatType chatType : ChatType.values()) {
            if (chatType.getDisplayName().equals(s))
                return chatType;
        }
        return null;
    }
}