package com.rettichlp.unicacityaddon.base.enums.faction;

import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;

/**
 * @author RettichLP
 */
public enum Faction {

    NULL("", "Keine Auswahl", "", -1, ""),
    FBI("fbi", "FBI", "FBI", 106, Message.getBuilder()
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

    CALDERON("calderon", "Calderón Kartell", "Kartell", 154, Message.getBuilder()
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
    LEMILIEU("le_milieu", "Le Milieu", "France", 179, Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("Ⓜ").color(ColorCode.DARK_AQUA).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    OBRIEN("obrien", "O'brien Familie", "Obrien", 191, Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("☘").color(ColorCode.DARK_GREEN).advance()
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
    NEWS("news", "News Agency", "News", 239, Message.getBuilder()
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

    public int getPublicChannelId() {
        return publicChannelId;
    }

    public String getNameTagSuffix() {
        return nameTagSuffix;
    }

    public String getWebsiteUrl() {
        return this.equals(NULL) ? null : "https://unicacity.de/fraktionen/" + getApiName();
    }

    public static Faction getFactionByDisplayName(String s) {
        for (Faction faction : Faction.values()) {
            if (faction.getDisplayName().equalsIgnoreCase(s))
                return faction;
        }
        return null;
    }

    public static Faction getFactionByFactionKey(String s) {
        for (Faction faction : Faction.values()) {
            if (faction.getFactionKey().equalsIgnoreCase(s))
                return faction;
        }
        return null;
    }
}