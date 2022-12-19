package com.rettichlp.unicacityaddon.base.manager;

import com.google.gson.Gson;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.models.Data;
import com.rettichlp.unicacityaddon.commands.CoordlistCommand;
import com.rettichlp.unicacityaddon.commands.TodoListCommand;
import com.rettichlp.unicacityaddon.commands.faction.ServiceCountCommand;
import com.rettichlp.unicacityaddon.events.faction.EquipEventHandler;
import com.rettichlp.unicacityaddon.events.faction.rettungsdienst.FirstAidEventHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author RettichLP
 */
public class FileManager {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    public static File getMinecraftDir() {
        return new File(System.getenv("APPDATA") + "/.minecraft");
    }

    public static File getUnicacityAddonDir() {
        File unicacityAddonDir = new File(getMinecraftDir().getAbsolutePath() + "/unicacityAddon/");
        if (unicacityAddonDir.exists() || unicacityAddonDir.mkdir())
            return unicacityAddonDir;

        AbstractionLayer.getPlayer().sendErrorMessage("Ordner 'unicacityAddon' wurde nicht gefunden!");

        return null;
    }

    public static File getAddonScreenshotDir() {
        if (getUnicacityAddonDir() == null)
            return null;
        File addonScreenshotDir = new File(getUnicacityAddonDir().getAbsolutePath() + "/screenshots/");
        if (addonScreenshotDir.exists() || addonScreenshotDir.mkdir())
            return addonScreenshotDir;

        AbstractionLayer.getPlayer().sendErrorMessage("Ordner 'screenshots' wurde nicht gefunden!");

        return null;
    }

    public static File getAddonActivityScreenDir(String type) {
        if (getAddonScreenshotDir() == null)
            return null;
        File addonScreenshotDir = new File(getAddonScreenshotDir().getAbsolutePath() + "/" + type);
        if (addonScreenshotDir.exists() || addonScreenshotDir.mkdir())
            return addonScreenshotDir;

        AbstractionLayer.getPlayer().sendErrorMessage("Ordner 'screenshots/" + type + "' wurde nicht gefunden!");

        return null;
    }

    public static File getOptionsFile() {
        File optionsFile = new File(getMinecraftDir().getAbsolutePath() + "/options.txt");
        if (optionsFile.exists())
            return optionsFile;

        AbstractionLayer.getPlayer().sendErrorMessage("Datei 'options.txt' wurde nicht gefunden!");

        return null;
    }

    public static File getDataFile() throws IOException {
        if (getUnicacityAddonDir() == null)
            return null;
        File dataFile = new File(getUnicacityAddonDir().getAbsolutePath() + "/data.json");
        if (dataFile.exists() || dataFile.createNewFile())
            return dataFile;

        AbstractionLayer.getPlayer().sendErrorMessage("Datei 'data.json' wurde nicht gefunden!");

        return null;
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
            if (dataFile == null)
                return;
            Gson g = new Gson();
            String jsonData = FileUtils.readFileToString(dataFile, StandardCharsets.UTF_8.toString());

            if (jsonData.isEmpty()) {
//                MoneyHudWidget.bankBalance = 0;
//                MoneyHudWidget.cashBalance = 0;
//                JobModule.setBalance(0);
//                JobModule.setExperience(0);
//                PayDayModule.setTime(0);
                ServiceCountCommand.serviceCount = 0;
                FirstAidEventHandler.firstAidIssuingTime = 0;
                TodoListCommand.todolist = Collections.emptyList();
                CoordlistCommand.coordlist = Collections.emptyList();
                HouseDataManager.HOUSE_DATA = new HashMap<>();
                EquipEventHandler.equipLogEntryList = Collections.emptyList();
//                CarOpenModule.info = "";
                return;
            }

            Data data = g.fromJson(jsonData, Data.class);
//            MoneyHudWidget.bankBalance = data.getBankBalance();
//            MoneyHudWidget.cashBalance = data.getCashBalance();
//            JobModule.jobBalance = data.getJobBalance();
//            JobModule.jobExperience = data.getJobExperience();
//            PayDayModule.currentTime = data.getPayDayTime();
            ServiceCountCommand.serviceCount = data.getServiceCount();
            FirstAidEventHandler.firstAidIssuingTime = data.getFirstAidDate();
            TodoListCommand.todolist = data.getTodolist();
            CoordlistCommand.coordlist = data.getCoordlist();
            HouseDataManager.HOUSE_DATA = data.getHouseData();
            EquipEventHandler.equipLogEntryList = data.getEquipList();
//            CarOpenModule.info = data.getCarInfo() == null ? Strings.EMPTY : data.getCarInfo();
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
            if (dataFile == null)
                return;
            Gson g = new Gson();
            Data data = new Data();
//            data.setBankBalance(MoneyHudWidget.bankBalance);
//            data.setCashBalance(MoneyHudWidget.cashBalance);
//            data.setJobBalance(JobModule.jobBalance);
//            data.setJobExperience(JobModule.jobExperience);
//            data.setPayDayTime(PayDayModule.currentTime);
            data.setFirstAidDate(FirstAidEventHandler.firstAidIssuingTime);
            data.setTodolist(TodoListCommand.todolist);
            data.setCoordlist(CoordlistCommand.coordlist);
            data.setHouseData(HouseDataManager.HOUSE_DATA);
            data.setEquipList(EquipEventHandler.equipLogEntryList);
//            data.setCarInfo(CarOpenModule.info);
            data.setServiceCount(ServiceCountCommand.serviceCount);
            FileUtils.writeStringToFile(dataFile, g.toJson(data), StandardCharsets.UTF_8.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}