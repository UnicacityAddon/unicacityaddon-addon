package com.rettichlp.unicacityaddon.base.text;

import net.minecraft.util.text.TextFormatting;

import java.awt.Color;

/**
 * @author RettichLP
 */
public enum ColorCode {

    BLACK("§0", TextFormatting.BLACK, new Color(0, 0, 0)),
    DARK_BLUE("§1", TextFormatting.DARK_BLUE, new Color(0, 0, 170)),
    DARK_GREEN("§2", TextFormatting.DARK_GREEN, new Color(0, 170, 0)),
    DARK_AQUA("§3", TextFormatting.DARK_AQUA, new Color(0, 170, 170)),
    DARK_RED("§4", TextFormatting.DARK_RED, new Color(170, 0, 0)),
    DARK_PURPLE("§5", TextFormatting.DARK_PURPLE, new Color(170, 0, 170)),
    GOLD("§6", TextFormatting.GOLD, new Color(255, 170, 0)),
    GRAY("§7", TextFormatting.GRAY, new Color(170, 170, 170)),
    DARK_GRAY("§8", TextFormatting.DARK_GRAY, new Color(85, 85, 85)),
    BLUE("§9", TextFormatting.BLUE, new Color(85, 85, 255)),
    GREEN("§a", TextFormatting.GREEN, new Color(85, 255, 85)),
    AQUA("§b", TextFormatting.AQUA, new Color(85, 255, 255)),
    RED("§c", TextFormatting.RED, new Color(255, 85, 85)),
    LIGHT_PURPLE("§d", TextFormatting.LIGHT_PURPLE, new Color(255, 85, 255)),
    YELLOW("§e", TextFormatting.YELLOW, new Color(255, 255, 85)),
    WHITE("§f", TextFormatting.WHITE, new Color(255, 255, 255));

    private final String code;
    private final TextFormatting textFormatting;
    private final Color color;

    ColorCode(String code, TextFormatting textFormatting, Color color) {
        this.code = code;
        this.textFormatting = textFormatting;
        this.color = color;
    }

    public String getCode() {
        return code;
    }

    public TextFormatting getTextFormatting() {
        return textFormatting;
    }

    public Color getColor() {
        return color;
    }
}