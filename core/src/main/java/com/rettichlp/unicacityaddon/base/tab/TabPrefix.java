package com.rettichlp.unicacityaddon.base.tab;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TabPrefix {

    FBI_UC("§1[UC]"),
    FBI("§1"),
    POLICE_UC("§9[UC]"),
    POLICE("§9"),
    MEDIC_UC("§4[UC]"),
    MEDIC("§4"),
    NEWS_UC("§6[UC]"),
    NEWS("§6"),
    UC_DUTY("§8[§9UC§8]§c"),
    BUILDER("§8[§eB§8]"),
    REPORT("§8[§6R§8]"),
    UC("[UC]"),
    NONE("");

    private final String pattern;

    public static TabPrefix getTypeByDisplayName(String displayName) {
        return Arrays.stream(values())
                .filter(tabPrefix -> displayName.startsWith(tabPrefix.pattern))
                .findFirst()
                .orElse(NONE);
    }
}
