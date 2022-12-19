package com.rettichlp.unicacityaddon.base.enums.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.text.ChatType;

import java.util.regex.Pattern;

public enum ReinforcementType {
    DEFAULT("-f", ChatType.FCHAT, null),
    D_CHAT("-d", ChatType.DCHAT, null),
    RAM("-r", ChatType.FCHAT, "Rammen!"),
    RAM_D("-rd", ChatType.DCHAT, "Rammen!"),
    EMERGENCY("-e", ChatType.FCHAT, "Dringend!"),
    EMERGENCY_D("-ed", ChatType.DCHAT, "Dringend!"),
    MEDIC("-m", ChatType.DCHAT, "Medic benötigt!"),
    CORPSE_GUARDING("-lb", ChatType.DCHAT, "Leichenbewachung!"),
    DRUG_REMOVAL("-da", ChatType.DCHAT, "Drogenabnahme!"),
    CONTRACT("-ct", ChatType.FCHAT, "Contract!"),
    PLANT("-p", ChatType.DCHAT, "Plant!"),
    BOMB("-b", ChatType.DCHAT, "Bombe!"),
    HOSTAGE_TAKING("-gn", ChatType.DCHAT, "Geiselnahme!"),
    TRAINING("-t", ChatType.FCHAT, "Training!"),
    TRAINING_D("-td", ChatType.DCHAT, "Training!");

    private final String argument;
    private final ChatType chatType;
    private final String message;
    private final Pattern pattern;

    ReinforcementType(String argument, ChatType chatType, String message) {
        this.argument = argument;
        this.chatType = chatType;
        this.message = message;

        this.pattern = message != null ? Pattern.compile("^.+ ((?:\\[UC])*\\w+): " + message + "$") : null;
    }

    public String getArgument() {
        return argument;
    }

    public ChatType getChatType() {
        return UnicacityAddon.configuration.nameTagSetting().allianceFactionNameTagSetting().enabled().get() ? chatType : ChatType.FCHAT;
    }

    public String getMessage() {
        return message;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public static ReinforcementType getByArgument(String s) {
        for (ReinforcementType t : ReinforcementType.values()) {
            if (t.getArgument().equalsIgnoreCase(s))
                return t;
        }
        return ReinforcementType.DEFAULT;
    }
}