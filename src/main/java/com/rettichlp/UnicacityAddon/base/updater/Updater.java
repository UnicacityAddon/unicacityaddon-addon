package com.rettichlp.UnicacityAddon.base.updater;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Updater {

    public static void updateChecker() {
        String latestVersion = getLatestVersion();
        if (latestVersion.equals(UnicacityAddon.VERSION)) return;

        UnicacityAddon.LABYMOD.notifyMessageRaw(ColorCode.AQUA.getCode() + "Update verfügbar!", "Es ist Version " + ColorCode.DARK_AQUA.getCode() + "v" + latestVersion + ColorCode.WHITE.getCode() + " verfügbar.");

        AbstractionLayer.getPlayer().sendMessage(Message.getBuilder()
                .info().space()
                .of("Es ist ein neues Update für").advance().space()
                .of("UnicacityAddon")
                        .color(ColorCode.DARK_AQUA)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                .of("Changelog").color(ColorCode.RED).advance()
                                .createComponent())
                        .clickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/rettichlp/UnicacityAddon-1.12.2/releases/latest")
                        .advance().space()
                .of("verfügbar!").advance().space()
                .createComponent());
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