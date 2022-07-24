package com.rettichlp.UnicacityAddon.base.text;

import net.minecraft.util.text.TextFormatting;

/**
 * @author RettichLP
 */
public enum ColorCode {

    BLACK("§0", TextFormatting.BLACK),
    DARK_BLUE("§1", TextFormatting.DARK_BLUE),
    DARK_GREEN("§2", TextFormatting.DARK_GREEN),
    DARK_AQUA("§3", TextFormatting.DARK_AQUA),
    DARK_RED("§4", TextFormatting.DARK_RED),
    DARK_PURPLE("§5", TextFormatting.DARK_PURPLE),
    GOLD("§6", TextFormatting.GOLD),
    GRAY("§7", TextFormatting.GRAY),
    DARK_GRAY("§8", TextFormatting.DARK_GRAY),
    BLUE("§9", TextFormatting.BLUE),
    GREEN("§a", TextFormatting.GREEN),
    AQUA("§b", TextFormatting.AQUA),
    RED("§c", TextFormatting.RED),
    LIGHT_PURPLE("§d", TextFormatting.LIGHT_PURPLE),
    YELLOW("§e", TextFormatting.YELLOW),
    WHITE("§f", TextFormatting.WHITE);

    private final String code;
    private final TextFormatting textFormatting;

    ColorCode(String code, TextFormatting textFormatting) {
        this.code = code;
        this.textFormatting = textFormatting;
    }

    public String getCode() {
        return code;
    }

    public TextFormatting getTextFormatting() {
        return textFormatting;
    }
}
