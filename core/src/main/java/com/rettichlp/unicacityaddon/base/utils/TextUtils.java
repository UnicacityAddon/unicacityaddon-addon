package com.rettichlp.unicacityaddon.base.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

/**
 * @author RettichLP
 */
public class TextUtils {

    /**
     * Converts textComponent to a legacy string - <code>&6Hello &b&lworld&c!</code>
     *
     * @param component component to be converted to formatted string
     * @return converted string of component
     */
    public static String legacy(Component component) {
        return LegacyComponentSerializer.legacyAmpersand().serialize(component);
    }

    /**
     * Converts textComponent to a plain string - <code>Hello world!</code>
     *
     * @param component component to be converted to unformatted string
     * @return converted string of component
     */
    public static String plain(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    public static String makeStringByArgs(Object[] args, String space) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o : args) {
            stringBuilder.append(o).append(space);
        }
        return stringBuilder.substring(0, stringBuilder.length() - space.length());
    }
}
