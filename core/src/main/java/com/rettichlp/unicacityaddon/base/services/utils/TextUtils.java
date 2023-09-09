package com.rettichlp.unicacityaddon.base.services.utils;

import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.FormattingCode;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author RettichLP
 */
public class TextUtils {

    public TextUtils() {
    }

    /**
     * Converts textComponent to a legacy string - <code>&6Hello &b&lworld&c!</code>
     *
     * @param component Component to be converted to formatted string
     * @return converted string of component
     */
    public String legacy(Component component) {
        if (component instanceof TextComponent textComponent) {
            String text = textComponent.getText();
            Style style = textComponent.style();

            StringBuilder stringBuilder = new StringBuilder();
            for (TextDecoration textDecoration : TextDecoration.values()) {
                if (style.isDecorationSet(textDecoration)) {
                    stringBuilder.append(switch (textDecoration) {
                        case OBFUSCATED -> FormattingCode.OBFUSCATED.getCode();
                        case BOLD -> FormattingCode.BOLD.getCode();
                        case STRIKETHROUGH -> FormattingCode.STRIKETHROUGH.getCode();
                        case UNDERLINED -> FormattingCode.UNDERLINED.getCode();
                        case ITALIC -> FormattingCode.ITALIC.getCode();
                    });
                }
            }

            for (ColorCode colorCode : ColorCode.values()) {
                if (style.getColor() != null && style.getColor().equals(colorCode.getTextColor())) {
                    stringBuilder.append(colorCode.getCode());
                }
            }

            stringBuilder.append(text);

            List<Component> children = component.getChildren();
            children.forEach(c -> stringBuilder.append(legacy(c)));

            return stringBuilder.toString();
        }

        return "";
    }

    /**
     * Converts textComponent to a plain string - <code>Hello world!</code>
     *
     * @param component Component to be converted to unformatted string
     * @return converted string of component
     */
    public String plain(Component component) {
        if (component instanceof TextComponent textComponent) {
            String text = textComponent.getText();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(text);

            List<Component> children = component.getChildren();
            children.forEach(c -> stringBuilder.append(plain(c)));

            return stringBuilder.toString();
        }

        return "";
    }

    /**
     * Removes the color code with <code>§</code> or <code>&</code> prefix from text
     *
     * @param text String starting with a color code containing <code>§</code> or <code>&</code>
     * @return text without color code prefix
     */
    public String stripColor(String text) {
        return PatternHandler.STRIP_COLOR_PATTERN.matcher(text).replaceAll("");
    }

    /**
     * Removes a server provided player prefix from text. A player prefix is a text starting with <code>[</code> and ending with <code>]</code>
     *
     * @param text String without server provided prefix
     * @return text without server provided prefix
     */
    public String stripPrefix(String text) {
        return PatternHandler.STRIP_PREFIX_PATTERN.matcher(text).replaceAll("");
    }

    /**
     * Converts a given time in seconds to an easy readable time in <code>HH:mm:ss</code> format or <code>mm:ss</code> if duration is less than an hour
     *
     * @param seconds time in seconds
     * @return converted time in readable format
     */
    public String parseTimer(long seconds) {
        return seconds >= 3600 ? String.format("%02d:%02d:%02d", seconds / 3600, seconds % 3600 / 60, seconds % 60) : String.format("%02d:%02d", seconds / 60, seconds % 60);
    }

    /**
     * Converts a given time in milliseconds to an easy readable time in <code>XXd XXh XXm XXs</code> format
     *
     * @param milliseconds time in milliseconds
     * @return converted time in readable format
     */
    public String parseTimerWithTimeUnit(long milliseconds) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        long minutes = TimeUnit.SECONDS.toMinutes(seconds);
        long hours = TimeUnit.MINUTES.toHours(minutes);
        long days = TimeUnit.HOURS.toDays(hours);
        return String.format("%02dd %02dh %02dm %02ds", days, hours % 24, minutes % 60, seconds % 60);
    }

    /**
     * Converts a given <code>Long</code> with specific <code>TimeUnit</code> to an easy readable time <code>String</code>
     *
     * @param timeUnit TimeUnit in which the given value is provided
     * @param time     Long of the time to be converted
     * @return converted time in readable format
     */
    public String parseTime(TimeUnit timeUnit, long time) {
        long dd = timeUnit.toDays(time);
        long hh = timeUnit.toHours(time) % 24;
        long mm = timeUnit.toMinutes(time) % 60;
        long ss = timeUnit.toSeconds(time) % 60;

        String days = dd > 0 ? dd + ":" : "";
        String hours = dd > 0 || hh > 0 ? (hh < 10 ? "0" + hh : hh) + ":" : "";
        String minutes = (mm < 10 ? "0" + mm : mm) + ":";
        String seconds = (ss < 10 ? "0" + ss : String.valueOf(ss));

        return days + hours + minutes + seconds;
    }

    /**
     * Converts an array of <code>Object</code> to a <code>String</code> with the space between the objects
     *
     * @param args  Array of <code>Object</code>
     * @param space String of space between objects
     * @return String of objects with the space between them
     */
    public String makeStringByArgs(Object[] args, String space) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o : args) {
            stringBuilder.append(o).append(space);
        }
        return stringBuilder.substring(0, stringBuilder.length() - space.length());
    }

    public <T> T getMostMatching(Iterable<T> list, String input, Function<T, String> toStringFunction) {
        input = input.toLowerCase();

        int delta = Integer.MAX_VALUE;
        T found = null;
        for (T t : list) {
            String string = toStringFunction.apply(t).toLowerCase();
            if (!string.startsWith(input))
                continue;

            int curDelta = Math.abs(string.length() - input.length());
            if (curDelta < delta) {
                found = t;
                delta = curDelta;
            }

            if (curDelta == 0)
                break;
        }

        return found;
    }

    /**
     * Transform a {@link String} in which every word starts with an uppercase letter and all other letters are lowercase.
     *
     * @param string String to be transformed
     * @return a string in which all letters are lowercase without letters at word beginnings
     */
    public String createTitle(String string) {
        StringBuilder titleBuilder = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : string.replace("-", " ").replace("_", " ").toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }
            titleBuilder.append(c);
        }

        return titleBuilder.toString();
    }
}
