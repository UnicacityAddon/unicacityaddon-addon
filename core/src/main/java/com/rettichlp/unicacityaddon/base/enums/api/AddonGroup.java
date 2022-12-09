package com.rettichlp.unicacityaddon.base.enums.api;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public enum AddonGroup {
    BETA("BETA", "UCAddon Beta-Tester", new Color(217, 59, 228, 1), new ArrayList<>()),
    BLACKLIST("BLACKLIST", "", null, new ArrayList<>()),
    CEO("CEO", "UCAddon CEO", new Color(228, 59, 59, 1), new ArrayList<>()),
    DEV("DEV", "UCAddon Developer", new Color(96, 159, 238, 1), new ArrayList<>()),
    DYAVOL("DYAVOL", "", null, new ArrayList<>()),
    LEMILIEU("LEMILIEU", "", null, new ArrayList<>()),
    MOD("MOD", "UCAddon Moderator", new Color(96, 159, 238, 1), new ArrayList<>()),
    SUP("SUP", "UCAddon Supporter", new Color(228, 110, 59, 1), new ArrayList<>()),
    VIP("VIP", "UCAddon VIP", new Color(227, 228, 59, 1), new ArrayList<>());

    private final String apiName;
    private final String displayName;
    private final Color color;
    private final List<String> memberList;

    AddonGroup(String apiName, String displayName, Color color, List<String> memberList) {
        this.apiName = apiName;
        this.displayName = displayName;
        this.color = color;
        this.memberList = memberList;
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

    public List<String> getMemberList() {
        return memberList;
    }
}