package com.rettichlp.UnicacityAddon.base.text;

/**
 * @author RettichLP
 */
public enum FormattingCode {

    OBFUSCATED("§k"),
    BOLD("§l"),
    STRIKETHROUGH("§m"),
    UNDERLINE("§n"),
    ITALIC("§o"),
    RESET("§r");

    private final String code;

    FormattingCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
