package com.rettichlp.unicacityaddon.base.text;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
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

    public String getChatCommand() {
        return this.equals(CHAT) ? "" : "/" + getChatType();
    }

    public static ChatType getChatTypeByDisplayName(String s) {
        for (ChatType chatType : ChatType.values()) {
            if (chatType.getDisplayName().equals(s))
                return chatType;
        }
        return null;
    }
}