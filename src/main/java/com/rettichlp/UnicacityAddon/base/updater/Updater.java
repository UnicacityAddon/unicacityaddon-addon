package com.rettichlp.UnicacityAddon.base.updater;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Updater {

    public static void update() {
        String latestVersion = getLatestVersion();
        if (latestVersion.equals(UnicacityAddon.VERSION)) return;

        UnicacityAddon.LABYMOD.notifyMessageRaw(ColorCode.RED.getCode() + "Update verfügbar!", "Es ist Version " + ColorCode.GOLD.getCode() + latestVersion + ColorCode.WHITE.getCode() + " verfügbar.");
    }

    private static String getLatestVersion() {
        try {
            URLConnection con = new URL("https://github.com/rettichlp/UnicacityAddon-1.12.2/releases/latest").openConnection();
            con.connect();
            InputStream is = con.getInputStream();
            is.close();
            return con.getURL().toString().replace("https://github.com/rettichlp/UnicacityAddon-1.12.2/releases/tag/v", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}