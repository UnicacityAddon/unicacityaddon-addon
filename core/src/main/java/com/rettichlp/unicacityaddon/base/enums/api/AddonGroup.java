package com.rettichlp.unicacityaddon.base.enums.api;

import com.rettichlp.unicacityaddon.base.text.ColorCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
public enum AddonGroup {
    CEO("CEO", "UCAddon CEO", ColorCode.RED, new ArrayList<>()),
    DEV("DEV", "UCAddon Developer", ColorCode.AQUA, new ArrayList<>()),
    MOD("MOD", "UCAddon Moderator", ColorCode.BLUE, new ArrayList<>()),
    SUP("SUP", "UCAddon Supporter", ColorCode.GOLD, new ArrayList<>()),
    BETA("BETA", "UCAddon Beta-Tester", ColorCode.DARK_PURPLE, new ArrayList<>()),
    VIP("VIP", "UCAddon VIP", ColorCode.YELLOW, new ArrayList<>()),
    BLACKLIST("BLACKLIST", "", null, new ArrayList<>()),
    DYAVOL("DYAVOL", "", null, new ArrayList<>());

    private final String apiName;
    private final String displayName;
    private final ColorCode colorCode;
    private final List<String> memberList;

    AddonGroup(String apiName, String displayName, ColorCode colorCode, List<String> memberList) {
        this.apiName = apiName;
        this.displayName = displayName;
        this.colorCode = colorCode;
        this.memberList = memberList;
    }

    public String getApiName() {
        return apiName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ColorCode getColorCode() {
        return colorCode;
    }

    public List<String> getMemberList() {
        return memberList;
    }

    public static List<AddonGroup> getAddonGroupsOfPlayer(String playerName) {
        return Arrays.stream(AddonGroup.values())
                .filter(addonGroup -> addonGroup.memberList.contains(playerName))
                .sorted()
                .collect(Collectors.toList());
    }
}