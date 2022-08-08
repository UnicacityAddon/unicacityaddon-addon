package com.rettichlp.UnicacityAddon.base.io;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author RettichLP
 */
public class FileManager {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");

    public static File getMinecraftDir() {
        return UnicacityAddon.MINECRAFT.mcDataDir;
    }

    public static File getUnicacityAddonDir() {
        File unicacityAddonDir = new File(getMinecraftDir().getAbsolutePath() + "/unicacityAddon/");
        if (unicacityAddonDir.exists() || unicacityAddonDir.mkdir()) return unicacityAddonDir;

        AbstractionLayer.getPlayer().sendErrorMessage("Ordner 'unicacityAddon' wurde nicht gefunden!");

        return null;
    }

    public static File getAddonScreenshotDir() {
        if (getUnicacityAddonDir() == null) return null;
        File addonScreenshotDir = new File(getUnicacityAddonDir().getAbsolutePath() + "/screenshots/");
        if (addonScreenshotDir.exists() || addonScreenshotDir.mkdir()) return addonScreenshotDir;

        AbstractionLayer.getPlayer().sendErrorMessage("Ordner 'screenshots' wurde nicht gefunden!");

        return null;
    }

    public static File getBlacklistDataFile() throws IOException {
        if (getUnicacityAddonDir() == null) return null;
        File blacklistDataFile = new File(getUnicacityAddonDir().getAbsolutePath() + "/blacklistData.json");
        if (blacklistDataFile.exists() || blacklistDataFile.createNewFile()) return blacklistDataFile;

        AbstractionLayer.getPlayer().sendErrorMessage("Datei 'blacklistData.json' wurde nicht gefunden!");

        return null;
    }

    public static File getOfflineDataFile() throws IOException {
        if (getUnicacityAddonDir() == null) return null;
        File offlineDataFile = new File(getUnicacityAddonDir().getAbsolutePath() + "/offlineData.json");
        if (offlineDataFile.exists() || offlineDataFile.createNewFile()) return offlineDataFile;

        AbstractionLayer.getPlayer().sendErrorMessage("Datei 'offlineData.json' wurde nicht gefunden!");

        return null;
    }

    public static File getNewImageFile() throws IOException {
        if (getAddonScreenshotDir() == null) return null;

        String date = DATE_FORMAT.format(new Date());
        StringBuilder sb = new StringBuilder(date);
        int i = 1;
        while (new File(getAddonScreenshotDir().getAbsolutePath() + "/" + sb + ".jpg").exists()) {
            if (i == 1) sb.append("_").append(i++);
            else sb.replace(sb.length() - 1, sb.length(), String.valueOf(i));
        }

        File newImageFile = new File(getAddonScreenshotDir().getAbsolutePath() + "/" + sb + ".jpg");
        return newImageFile.createNewFile() ? newImageFile : null;
    }
}