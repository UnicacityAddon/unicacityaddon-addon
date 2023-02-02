package com.rettichlp.unicacityaddon.base.text;

import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;

import java.awt.Color;
import java.util.Arrays;

/**
 * @author RettichLP
 */
public enum ColorCode {

    BLACK("§0", NamedTextColor.BLACK, new Color(0, 0, 0)),
    DARK_BLUE("§1", NamedTextColor.DARK_BLUE, new Color(0, 0, 170)),
    DARK_GREEN("§2", NamedTextColor.DARK_GREEN, new Color(0, 170, 0)),
    DARK_AQUA("§3", NamedTextColor.DARK_AQUA, new Color(0, 170, 170)),
    DARK_RED("§4", NamedTextColor.DARK_RED, new Color(170, 0, 0)),
    DARK_PURPLE("§5", NamedTextColor.DARK_PURPLE, new Color(170, 0, 170)),
    GOLD("§6", NamedTextColor.GOLD, new Color(255, 170, 0)),
    GRAY("§7", NamedTextColor.GRAY, new Color(170, 170, 170)),
    DARK_GRAY("§8", NamedTextColor.DARK_GRAY, new Color(85, 85, 85)),
    BLUE("§9", NamedTextColor.BLUE, new Color(85, 85, 255)),
    GREEN("§a", NamedTextColor.GREEN, new Color(85, 255, 85)),
    AQUA("§b", NamedTextColor.AQUA, new Color(85, 255, 255)),
    RED("§c", NamedTextColor.RED, new Color(255, 85, 85)),
    LIGHT_PURPLE("§d", NamedTextColor.LIGHT_PURPLE, new Color(255, 85, 255)),
    YELLOW("§e", NamedTextColor.YELLOW, new Color(255, 255, 85)),
    WHITE("§f", NamedTextColor.WHITE, new Color(255, 255, 255));

    private final String code;
    private final TextColor textColor;
    private final Color color;

    ColorCode(String code, TextColor textColor, Color color) {
        this.code = code;
        this.textColor = textColor;
        this.color = color;
    }

    public String getCode() {
        return code;
    }

    public TextColor getTextColor() {
        return textColor;
    }

    public Color getColor() {
        return color;
    }

    public static ColorCode getColorCodeByTextColor(TextColor color) {
        return Arrays.stream(values())
                .filter(colorCode -> colorCode.textColor.equals(color))
                .findFirst()
                .orElse(WHITE);
    }
}