package com.rettichlp.UnicacityAddon.base.faction;

import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.WebsiteUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
public enum Faction {

    NULL("", "Keine Auswahl", "", -1, ""),
    FBI("fbi", "F.B.I", "FBI", 106, Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("✯").color(ColorCode.DARK_BLUE).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    POLIZEI("polizei", "Polizei", "Polizei", 78, Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("✯").color(ColorCode.BLUE).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    RETTUNGSDIENST("rettungsdienst", "Rettungsdienst", "Rettungsdienst", 118, Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("✚").color(ColorCode.DARK_RED).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),

    CALDERON("calderon", "Calderon Kartell", "Kartell", 154, Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("☀").color(ColorCode.GOLD).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    KERZAKOV("kerzakov", "Kerzakov Familie", "Kerzakov", 166, Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("✮").color(ColorCode.RED).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    LACOSANOSTRA("lacosanostra", "La Cosa Nostra", "Mafia", 130, Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("⚜").color(ColorCode.DARK_AQUA).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    OBRIEN("obrien", "O'brien", "Obrien", 191, Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("☘").color(ColorCode.DARK_GREEN).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    TRIADEN("triaden", "Triaden", "Triaden", 179, Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("是").color(ColorCode.RED).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    WESTSIDEBALLAS("westsideballas", "Westside Ballas", "Gang", 142, Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("☠").color(ColorCode.DARK_PURPLE).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),

    HITMAN("hitman", "Hitman", "Hitman", 215, Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("➹").color(ColorCode.AQUA).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    KIRCHE("kirche", "Kirche", "Kirche", 227, Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("†").color(ColorCode.LIGHT_PURPLE).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    NEWS("news", "News", "News", 239, Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("✉").color(ColorCode.YELLOW).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    TERRORISTEN("terroristen", "Terroristen", "Terroristen", 203, Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("❇").color(ColorCode.GRAY).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create());

    private final String apiName;
    private final String displayName;
    private final String factionKey;
    private final int publicChannelId;
    private final String nameTagSuffix;

    Faction(String apiName, String displayName, String factionKey, int publicChannelId, String nameTagSuffix) {
        this.apiName = apiName;
        this.displayName = displayName;
        this.factionKey = factionKey;
        this.publicChannelId = publicChannelId;
        this.nameTagSuffix = nameTagSuffix;
    }

    public String getApiName() {
        return this.apiName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getFactionKey() {
        return this.factionKey;
    }

    public String getNameTagSuffix() {
        return this.nameTagSuffix;
    }

    public String getWebsiteUrl() {
        if (this.equals(Faction.NULL)) return null;
        return "https://unicacity.de/fraktionen/" + getApiName();
    }

    public String getWebsiteSource() {
        return WebsiteUtils.websiteToString(this.getWebsiteUrl());
    }

    public List<String> getMember() {
        return FactionHandler.getPlayerFactionMap().entrySet().stream()
                .filter(entry -> entry.getValue().equals(this))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static Faction getFactionByFactionKey(String s) {
        for (Faction faction : Faction.values()) {
            if (faction.getFactionKey().equalsIgnoreCase(s)) return faction;
        }
        return null;
    }

    public int getPublicChannelId() {
        return publicChannelId;
    }
}