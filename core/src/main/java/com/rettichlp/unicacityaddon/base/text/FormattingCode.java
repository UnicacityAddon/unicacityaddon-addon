package com.rettichlp.unicacityaddon.base.text;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum FormattingCode {

    OBFUSCATED("§k"),
    BOLD("§l"),
    STRIKETHROUGH("§m"),
    UNDERLINE("§n"),
    ITALIC("§o"),
    RESET("§r");

    private final String code;
}
