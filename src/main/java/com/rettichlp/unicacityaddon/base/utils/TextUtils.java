package com.rettichlp.unicacityaddon.base.utils;

/**
 * @author RettichLP
 */
public class TextUtils {

    public static String parseTimer(int seconds) {
        return seconds >= 3600 ? String.format("%02d:%02d:%02d", seconds / 3600, seconds % 3600 / 60, seconds % 60) : String.format("%02d:%02d", seconds / 60, seconds % 60);
    }

    public static String makeStringByArgs(Object[] args, String space) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o : args) {
            stringBuilder.append(o).append(space);
        }
        return stringBuilder.substring(0, stringBuilder.length() - space.length());
    }
}
