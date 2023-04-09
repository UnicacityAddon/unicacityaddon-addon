package com.rettichlp.unicacityaddon.base.tab;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum TabPrefix {
    FBI_UC(Pattern.compile("^§1\\[UC]\\w+§r$"), Pattern.compile("^§1\\[UC]\\w+$")),
    FBI(Pattern.compile("^§1\\w+§r$"), Pattern.compile("^§1\\w+$")),
    POLICE_UC(Pattern.compile("^§9\\[UC]\\w+§r$"), Pattern.compile("^§9\\[UC]\\w+$")),
    POLICE(Pattern.compile("^§9\\w+§r$"), Pattern.compile("^§9\\w+$")),
    MEDIC_UC(Pattern.compile("^§4\\[UC]\\w+§r$"), Pattern.compile("^§4\\[UC]\\w+$")),
    MEDIC(Pattern.compile("^§4\\w+§r$"), Pattern.compile("^§4\\w+$")),
    NEWS_UC(Pattern.compile("^§6\\[UC]\\w+§r$"), Pattern.compile("^§6\\[UC]\\w+$")),
    NEWS(Pattern.compile("^§6\\w+§r$"), Pattern.compile("^§6\\w+$")),
    UC_DUTY(Pattern.compile("^§8\\[§r§9UC§r§8]§r§c\\w+§r$"), Pattern.compile("^§8\\[§9UC§8]§c\\w+$")),
    BUILDER(Pattern.compile("^§8\\[§r§eB§r§8]§r\\w+§r$"), Pattern.compile("^§8\\[§eB§8]\\w+$")),
    REPORT(Pattern.compile("^§8\\[§r§6R§r§8]§r\\w+§r$"), Pattern.compile("^§8\\[§6R§8]\\w+$")),
    UC(Pattern.compile("^\\[UC]\\w+§r$"), Pattern.compile("^\\[UC]\\w+$")),
    NONE(Pattern.compile("[^§]\\w+"), Pattern.compile("[^§]\\w+"));

    private final Pattern pattern_1_12_2;
    private final Pattern pattern_1_16_5;

    TabPrefix(Pattern pattern_1_12_2, Pattern pattern_1_16_5) {
        this.pattern_1_12_2 = pattern_1_12_2;
        this.pattern_1_16_5 = pattern_1_16_5;

    }

    public Pattern getPattern_1_12_2() {
        return pattern_1_12_2;
    }

    public Pattern getPattern_1_16_5() {
        return pattern_1_16_5;
    }

    public static TabPrefix getTypeByDisplayName(String displayName) {
        return Arrays.stream(values())
                .filter(tabPrefix -> tabPrefix.pattern_1_12_2.matcher(displayName).find() || tabPrefix.pattern_1_16_5.matcher(displayName).find())
                .findFirst()
                .orElse(NONE);
    }
}
