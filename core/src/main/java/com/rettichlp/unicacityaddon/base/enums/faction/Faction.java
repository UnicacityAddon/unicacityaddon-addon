package com.rettichlp.unicacityaddon.base.enums.faction;

import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum Faction {

    NULL("", "Keine Auswahl", "", false, -1, -1, ColorCode.GRAY, ""),
    FBI("fbi", "FBI", "FBI", false, 106, 107, ColorCode.DARK_BLUE, "✯"),
    POLIZEI("polizei", "Polizei", "Polizei", false, 78, 79, ColorCode.BLUE, "✯"),
    RETTUNGSDIENST("rettungsdienst", "Rettungsdienst", "Rettungsdienst", false, 118, 119, ColorCode.DARK_RED, "✚"),

    CALDERON("calderon", "Calderón Kartell", "Kartell", true, 154, 155, ColorCode.GOLD, "☀"),
    KERZAKOV("kerzakov", "Kerzakov Familie", "Kerzakov", true, 166, 167, ColorCode.RED, "✮"),
    LACOSANOSTRA("lacosanostra", "La Cosa Nostra", "Mafia", true, 130, 131, ColorCode.DARK_AQUA, "⚜"),
    LEMILIEU("le_milieu", "Le Milieu", "France", true, 179, 180, ColorCode.DARK_AQUA, "Ⓜ"),
    OBRIEN("obrien", "O'brien Familie", "Obrien", true, 191, 192, ColorCode.DARK_GREEN, "☘"),
    WESTSIDEBALLAS("westsideballas", "Westside Ballas", "Gang", true, 142, 143, ColorCode.DARK_PURPLE, "☠"),

    HITMAN("hitman", "Hitman", "Hitman", false, 215, 216, ColorCode.AQUA, "➹"),
    KIRCHE("kirche", "Kirche", "Kirche", false, 227, 228, ColorCode.LIGHT_PURPLE, "†"),
    NEWS("news", "News Agency", "News", false, 239, 240, ColorCode.YELLOW, "✉"),
    TERRORISTEN("terroristen", "Terroristen", "Terroristen", false, 203, 204, ColorCode.GRAY, "❇");

    private final String apiName;
    private final String displayName;
    private final String factionKey;
    private final boolean isBadFaction;
    private final int publicChannelId;
    private final int abstractedChannelId;
    private final ColorCode color;
    private final String icon;

    public String getWebsiteUrl() {
        return this.equals(NULL) ? null : "https://unicacity.de/fraktionen/" + getApiName();
    }

    public String getNameTagSuffix() {
        return !this.equals(NULL) ? Message.getBuilder()
                .of("◤").color(ColorCode.DARK_GRAY).advance()
                .of(this.icon).color(this.color).advance()
                .of("◢").color(ColorCode.DARK_GRAY).advance().create() : "";
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