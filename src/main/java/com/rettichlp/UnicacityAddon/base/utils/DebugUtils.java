package com.rettichlp.UnicacityAddon.base.utils;

import com.rettichlp.UnicacityAddon.base.api.TokenManager;

/**
 * @author RettichLP
 */
public class DebugUtils {

    public static void Debug(Class c, String s) {
        String stringBuilder = "[DEBUG] " +
                c.getSimpleName() + ": " +
                s.replace(TokenManager.API_TOKEN, "TOKEN");
        System.out.println(stringBuilder);
    }
}