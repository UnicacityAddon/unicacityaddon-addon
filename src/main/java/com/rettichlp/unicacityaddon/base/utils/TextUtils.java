package com.rettichlp.unicacityaddon.base.utils;

import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
public class TextUtils {

    public static String parseTimer(long seconds) {
        return seconds >= 3600 ? String.format("%02d:%02d:%02d", seconds / 3600, seconds % 3600 / 60, seconds % 60) : String.format("%02d:%02d", seconds / 60, seconds % 60);
    }

    public static String parseTimerWithTimeUnit(long milliseconds) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        long minutes = TimeUnit.SECONDS.toMinutes(seconds);
        long hours = TimeUnit.MINUTES.toHours(minutes);
        long days = TimeUnit.HOURS.toDays(hours);
        return String.format("%02dd %02dh %02dm %02ds", days, hours % 24, minutes % 60, seconds % 60);
    }

    public static String makeStringByArgs(Object[] args, String space) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o : args) {
            stringBuilder.append(o).append(space);
        }
        return stringBuilder.substring(0, stringBuilder.length() - space.length());
    }
}
