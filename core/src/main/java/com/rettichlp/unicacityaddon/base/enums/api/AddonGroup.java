package com.rettichlp.unicacityaddon.base.enums.api;

import com.rettichlp.unicacityaddon.base.text.ColorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum AddonGroup {

    CEO("CEO", "CEO", ColorCode.RED, new ArrayList<>()),
    DEV("DEV", "Developer", ColorCode.AQUA, new ArrayList<>()),
    MOD("MOD", "Moderator", ColorCode.BLUE, new ArrayList<>()),
    SUP("SUP", "Supporter", ColorCode.GOLD, new ArrayList<>()),
    BETA("BETA", "Beta-Tester", ColorCode.DARK_PURPLE, new ArrayList<>()),
    VIP("VIP", "VIP", ColorCode.YELLOW, new ArrayList<>()),
    BLACKLIST("BLACKLIST", "", null, new ArrayList<>()),
    DYAVOL("DYAVOL", "", null, new ArrayList<>());

    private final String apiName;
    private final String displayName;
    private final ColorCode colorCode;
    private final List<String> memberList;

    public static List<AddonGroup> getAddonGroupsOfPlayer(String playerName) {
        return Arrays.stream(AddonGroup.values())
                .filter(addonGroup -> addonGroup.memberList.contains(playerName))
                .sorted()
                .collect(Collectors.toList());
    }
}