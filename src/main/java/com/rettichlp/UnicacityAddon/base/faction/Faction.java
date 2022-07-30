package com.rettichlp.UnicacityAddon.base.faction;

import com.google.gson.Gson;
import com.rettichlp.UnicacityAddon.base.faction.blacklist.Blacklist;
import com.rettichlp.UnicacityAddon.base.faction.blacklist.BlacklistEntry;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.WebsiteAPI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
public enum Faction {

    NULL("", "Keine Auswahl", ""),
    CALDERON("calderon", "Calderon Kartell", Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("☀").color(ColorCode.GOLD).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    FBI("fbi", "F.B.I", Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("✯").color(ColorCode.DARK_BLUE).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    HITMAN("hitman", "Hitman", Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("➹").color(ColorCode.AQUA).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    KERZAKOV("kerzakov", "Kerzakov Familie", Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("✮").color(ColorCode.RED).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    KIRCHE("kirche", "Kirche", Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("†").color(ColorCode.LIGHT_PURPLE).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    LACOSANOSTRA("lacosanostra", "La Cosa Nostra", Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("⚜").color(ColorCode.DARK_AQUA).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    NEWS("news", "News", Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("✉").color(ColorCode.YELLOW).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    OBRIEN("obrien", "O'brien", Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("☘").color(ColorCode.DARK_GREEN).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    POLIZEI("polizei", "Polizei", Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("✯").color(ColorCode.BLUE).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    RETTUNGSDIENST("rettungsdienst", "Rettungsdienst", Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("✚").color(ColorCode.DARK_RED).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    TERRORISTEN("terroristen", "Terroristen", Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("❇").color(ColorCode.GRAY).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    TRIADEN("triaden", "Triaden", Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("是").color(ColorCode.RED).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create()),
    WESTSIDEBALLAS("westsideballas", "Westsideballas", Message.getBuilder()
            .of("◤").color(ColorCode.DARK_GRAY).advance()
            .of("☠").color(ColorCode.DARK_PURPLE).advance()
            .of("◢").color(ColorCode.DARK_GRAY).advance().create());

    private final String apiName;
    private final String displayName;
    private final String nameTagSuffix;

    Faction(String apiName, String displayName, String nameTagSuffix) {
        this.apiName = apiName;
        this.displayName = displayName;
        this.nameTagSuffix = nameTagSuffix;
    }

    public String getApiName() {
        return this.apiName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getNameTagSuffix() {
        return this.nameTagSuffix;
    }

    public String getWebsiteUrl() {
        if (this.equals(Faction.NULL)) return null;
        return "https://unicacity.de/fraktionen/" + getApiName();
    }

    public String getWebsiteSource() {
        return WebsiteAPI.websiteToString(this.getWebsiteUrl());
    }

    public List<String> getMember() {
        return FactionHandler.getPlayerFactionMap().entrySet().stream()
                .filter(entry -> entry.getValue().equals(this))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> getBlacklistReasons() {
        Gson g = new Gson();
        Blacklist blacklist = g.fromJson(Blacklist.getBlacklistData(), Blacklist.class);
        return blacklist.getBlacklist(this).stream().map(BlacklistEntry::getReason).collect(Collectors.toList());
    }
}
