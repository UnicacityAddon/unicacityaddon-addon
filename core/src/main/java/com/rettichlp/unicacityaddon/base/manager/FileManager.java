package com.rettichlp.unicacityaddon.base.manager;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.models.Data;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * <h3>File storage access</h3>
 * The addon uses data that is not important for all players. That's why they are not stored on my server via the API, but locally on the player's computer.<br>
 * This data contains, for example, the current account balance or the time until the next payday. This is used to be able to display Hudwidgets
 * immediately after joining the server and not to wait until a specific message is in the chat or to execute a command that supplies the said data.<br>
 * The data is saved in the Minecraft folder under an extra folder called <code>unicacityaddon</code>.
 *
 * @author RettichLP
 */
public class FileManager {

    public static Data DATA = new Data();

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
        String jsonData = "";
        try {
            File dataFile = FileManager.getDataFile();
            assert dataFile != null;
            jsonData = FileUtils.readFileToString(dataFile, StandardCharsets.UTF_8.toString());
        } catch (IOException e) {
            UnicacityAddon.LOGGER.error(e.getMessage());
        }

        try {
            JsonParser.parseString(jsonData); // validate check
            DATA = jsonData.isEmpty() ? new Data() : new Gson().fromJson(jsonData, Data.class);
        } catch (JsonSyntaxException e) {
            UnicacityAddon.LOGGER.info("Data cannot be created because Json is invalid: " + jsonData);
            UnicacityAddon.LOGGER.error(e.getMessage());
            UnicacityAddon.LOGGER.info("Creating default Data...");
            saveData();
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
                FileUtils.writeStringToFile(dataFile, g.toJson(DATA), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}