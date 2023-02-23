package com.rettichlp.unicacityaddon.base.manager;

import com.google.gson.Gson;
import com.rettichlp.unicacityaddon.base.models.Data;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author RettichLP
 */
public class FileManager {

    public static Data DATA;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    public static File getMinecraftDir() {
        return new File(System.getenv("APPDATA") + "/.minecraft");
    }

    public static File getUnicacityAddonDir() {
        File unicacityAddonDir = new File(getMinecraftDir().getAbsolutePath() + "/unicacityAddon/");
        return unicacityAddonDir.exists() || unicacityAddonDir.mkdir() ? unicacityAddonDir : null;
    }

    public static File getAddonScreenshotDir() {
        if (getUnicacityAddonDir() == null)
            return null;
        File addonScreenshotDir = new File(getUnicacityAddonDir().getAbsolutePath() + "/screenshots/");
        return addonScreenshotDir.exists() || addonScreenshotDir.mkdir() ? addonScreenshotDir : null;
    }

    public static File getAddonActivityScreenDir(String type) {
        if (getAddonScreenshotDir() == null)
            return null;
        File addonScreenshotDir = new File(getAddonScreenshotDir().getAbsolutePath() + "/" + type);
        return addonScreenshotDir.exists() || addonScreenshotDir.mkdir() ? addonScreenshotDir : null;
    }

    public static File getOptionsFile() {
        File optionsFile = new File(getMinecraftDir().getAbsolutePath() + "/options.txt");
        return optionsFile.exists() ? optionsFile : null;
    }

    public static File getDataFile() throws IOException {
        if (getUnicacityAddonDir() == null)
            return null;
        File dataFile = new File(getUnicacityAddonDir().getAbsolutePath() + "/data.json");
        return dataFile.exists() || dataFile.createNewFile() ? dataFile : null;
    }

    public static File getNewImageFile() throws IOException {
        if (getAddonScreenshotDir() == null)
            return null;

        String date = DATE_FORMAT.format(new Date());
        StringBuilder sb = new StringBuilder(date);
        int i = 1;
        while (new File(getAddonScreenshotDir().getAbsolutePath() + "/" + sb + ".jpg").exists()) {
            if (i == 1)
                sb.append("_").append(i++);
            else
                sb.replace(sb.length() - 1, sb.length(), String.valueOf(i));
        }

        File newImageFile = new File(getAddonScreenshotDir().getAbsolutePath() + "/" + sb + ".jpg");
        return newImageFile.createNewFile() ? newImageFile : null;
    }

    public static File getNewActivityImageFile(String type) throws IOException {
        if (getAddonActivityScreenDir(type) == null)
            return null;

        String date = DATE_FORMAT.format(new Date());
        StringBuilder sb = new StringBuilder(date);
        int i = 1;
        while (new File(Objects.requireNonNull(getAddonActivityScreenDir(type)).getAbsolutePath() + "/" + sb + "-" + type + ".jpg").exists()) {
            if (i == 1)
                sb.append("_").append(i++);
            else
                sb.replace(sb.length() - 1, sb.length(), String.valueOf(i));
        }

        File newImageFile = new File(Objects.requireNonNull(getAddonActivityScreenDir(type)).getAbsolutePath() + "/" + sb + "-" + type + ".jpg");
        return newImageFile.createNewFile() ? newImageFile : null;
    }

    public static void loadData() {
        try {
            File dataFile = FileManager.getDataFile();
            assert dataFile != null;
            String jsonData = null; // FileUtils.readFileToString(dataFile, StandardCharsets.UTF_8.toString()); todo
            DATA = jsonData == null || jsonData.equals("") || jsonData.equals("null") ? new Data() : new Gson().fromJson(jsonData, Data.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Quote: "Wenn du keine Brüste hast, rede ich nicht mehr mit dir!" - Dimiikou, 25.09.2022
     */
    public static void saveData() {
        try {
            File dataFile = FileManager.getDataFile();
            if (dataFile != null && DATA != null) {
                Gson g = new Gson();
//                FileUtils.writeStringToFile(dataFile, g.toJson(DATA), StandardCharsets.UTF_8.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}