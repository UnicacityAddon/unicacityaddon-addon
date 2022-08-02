package com.rettichlp.UnicacityAddon.base.utils;

/**
 * @author RettichLP
 */
public class TextUtils {

    public static String makeStringByArgs(Object[] args, String space) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o : args) {
            stringBuilder.append(o).append(space);
        }
        return stringBuilder.substring(0, stringBuilder.length() - space.length());
    }
}
