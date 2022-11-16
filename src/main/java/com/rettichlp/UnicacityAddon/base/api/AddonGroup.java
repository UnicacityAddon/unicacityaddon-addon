package com.rettichlp.UnicacityAddon.base.api;

import java.awt.*;

public enum AddonGroup {
    BETA("BETA", "UCAddon Beta-Tester", new Color(217, 59, 228, 1)),
    BLACKLIST("BLACKLIST", "", null),
    CEO("CEO", "UCAddon CEO", new Color(228, 59, 59, 1)),
    DEV("DEV", "UCAddon Developer", new Color(96, 159, 238, 1)),
    DYAVOL("DYAVOL", "", null),
    LEMILIEU("LEMILIEU", "", null),
    MOD("MOD", "UCAddon Moderator", new Color(96, 159, 238, 1)),
    SUP("SUP", "UCAddon Supporter", new Color(228, 110, 59, 1)),
    VIP("VIP", "UCAddon VIP", new Color(227, 228, 59, 1));

    private final String apiName;
    private final String displayName;
    private final Color color;

    AddonGroup(String apiName, String displayName, Color color) {
        this.apiName = apiName;
        this.displayName = displayName;
        this.color = color;
    }

    public String getApiName() {
        return apiName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Color getColor() {
        return color;
    }
}