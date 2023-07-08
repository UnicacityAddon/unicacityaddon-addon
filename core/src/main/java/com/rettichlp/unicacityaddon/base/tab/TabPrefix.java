package com.rettichlp.unicacityaddon.base.tab;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TabPrefix {

    FBI_UC("§1[UC]", "§1[UC]"),
    FBI("§1", "§1"),
    POLICE_UC("§9[UC]", "§9[UC]"),
    POLICE("§9", "§9"),
    MEDIC_UC("§4[UC]", "§4[UC]"),
    MEDIC("§4", "§4"),
    NEWS_UC("§6[UC]", "§6[UC]"),
    NEWS("§6", "§6"),
    UC_DUTY("§8[§r§9UC§r§8]§r§c", "§8[§9UC§8]§c"),
    BUILDER("§8[§r§eB§r§8]§r", "§8[§eB§8]"),
    REPORT("§8[§r§6R§r§8]§r", "^§8[§6R§8]"),
    UC("[UC]", "[UC]"),
    NONE("", "");

    private final String pattern_1_12_2;
    private final String pattern_1_16_5;

    public static TabPrefix getTypeByDisplayName(String displayName) {
        return Arrays.stream(values())
                .filter(tabPrefix -> displayName.startsWith(tabPrefix.pattern_1_12_2) || displayName.startsWith(tabPrefix.pattern_1_16_5))
                .findFirst()
                .orElse(NONE);
    }
}
