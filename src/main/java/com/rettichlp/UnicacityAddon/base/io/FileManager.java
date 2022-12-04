package com.rettichlp.UnicacityAddon.base.io;

import com.google.gson.Gson;
import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.json.Data;
import com.rettichlp.UnicacityAddon.base.json.HouseBankEntry;
import com.rettichlp.UnicacityAddon.commands.CoordlistCommand;
import com.rettichlp.UnicacityAddon.commands.TodoListCommand;
import com.rettichlp.UnicacityAddon.commands.faction.ServiceCountCommand;
import com.rettichlp.UnicacityAddon.events.faction.EquipEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.rettungsdienst.FirstAidEventHandler;
import com.rettichlp.UnicacityAddon.events.house.HouseBankEventHandler;
import com.rettichlp.UnicacityAddon.modules.BankMoneyModule;
import com.rettichlp.UnicacityAddon.modules.CarOpenModule;
import com.rettichlp.UnicacityAddon.modules.CashMoneyModule;
import com.rettichlp.UnicacityAddon.modules.JobModule;
import com.rettichlp.UnicacityAddon.modules.PayDayModule;
import joptsimple.internal.Strings;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

/**
 * @author RettichLP
 */
public class FileManager {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    public static File getMinecraftDir() {
        return UnicacityAddon.MINECRAFT.mcDataDir;
    }

    public static File getUnicacityAddonDir() {
        File unicacityAddonDir = new File(getMinecraftDir().getAbsolutePath() + "/unicacityAddon/");
        if (unicacityAddonDir.exists() || unicacityAddonDir.mkdir()) return unicacityAddonDir;

        AbstractionLayer.getPlayer().sendErrorMessage("Ordner 'unicacityAddon' wurde nicht gefunden!");

        return null;
    }

    public static File getLabyModAddonDir() {
        File labyModAddonDir = new File(getMinecraftDir().getAbsolutePath() + "/LabyMod/addons-1.12/");
        if (labyModAddonDir.exists() || labyModAddonDir.mkdirs()) return labyModAddonDir;

        AbstractionLayer.getPlayer().sendErrorMessage("Ordner 'addons-1.12' wurde nicht gefunden!");

        return null;
    }

    public static File getAddonScreenshotDir() {
        if (getUnicacityAddonDir() == null) return null;
        File addonScreenshotDir = new File(getUnicacityAddonDir().getAbsolutePath() + "/screenshots/");
        if (addonScreenshotDir.exists() || addonScreenshotDir.mkdir()) return addonScreenshotDir;

        AbstractionLayer.getPlayer().sendErrorMessage("Ordner 'screenshots' wurde nicht gefunden!");

        return null;
    }

    public static File getAddonActivityScreenDir(String type) {
        if (getAddonScreenshotDir() == null) return null;
        File addonScreenshotDir = new File(getAddonScreenshotDir().getAbsolutePath() + "/" + type);
        if (addonScreenshotDir.exists() || addonScreenshotDir.mkdir()) return addonScreenshotDir;

        AbstractionLayer.getPlayer().sendErrorMessage("Ordner 'screenshots/" + type + "' wurde nicht gefunden!");

        return null;
    }

    public static File getOptionsFile() {
        File optionsFile = new File(getMinecraftDir().getAbsolutePath() + "/options.txt");
        if (optionsFile.exists()) return optionsFile;

        AbstractionLayer.getPlayer().sendErrorMessage("Datei 'options.txt' wurde nicht gefunden!");

        return null;
    }

    public static File getUnicacityAddonFile() {
        if (getLabyModAddonDir() == null) return null;
        return new File(getLabyModAddonDir().getAbsolutePath() + "/UnicacityAddon-" + UnicacityAddon.VERSION + ".jar");
    }

    public static File getDataFile() throws IOException {
        if (getUnicacityAddonDir() == null) return null;
        File dataFile = new File(getUnicacityAddonDir().getAbsolutePath() + "/data.json");
        if (dataFile.exists() || dataFile.createNewFile()) return dataFile;

        AbstractionLayer.getPlayer().sendErrorMessage("Datei 'data.json' wurde nicht gefunden!");

        return null;
    }

    public static File getSharesDataFile() throws IOException {
        if (getUnicacityAddonDir() == null) return null;
        File sharesDataFile = new File(getUnicacityAddonDir().getAbsolutePath() + "/sharesData.json");
        if (sharesDataFile.exists() || sharesDataFile.createNewFile()) return sharesDataFile;

        AbstractionLayer.getPlayer().sendErrorMessage("Datei 'sharesData.json' wurde nicht gefunden!");

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

    public static File getNewActivityImageFile(String type) throws IOException {
        if (getAddonActivityScreenDir(type) == null) return null;

        String date = DATE_FORMAT.format(new Date());
        StringBuilder sb = new StringBuilder(date);
        int i = 1;
        while (new File(getAddonActivityScreenDir(type).getAbsolutePath() + "/" + sb + "-" + type + ".jpg").exists()) {
            if (i == 1) sb.append("_").append(i++);
            else sb.replace(sb.length() - 1, sb.length(), String.valueOf(i));
        }

        File newImageFile = new File(getAddonActivityScreenDir(type).getAbsolutePath() + "/" + sb + "-" + type + ".jpg");
        return newImageFile.createNewFile() ? newImageFile : null;
    }

    public static void loadData() {
        try {
            File dataFile = FileManager.getDataFile();
            if (dataFile == null) return;
            Gson g = new Gson();
            String jsonData = FileUtils.readFileToString(dataFile, StandardCharsets.UTF_8.toString());

            if (jsonData.isEmpty()) {
                BankMoneyModule.setBalance(0);
                CashMoneyModule.setBalance(0);
                JobModule.setBalance(0);
                JobModule.setExperience(0);
                PayDayModule.setTime(0);
                ServiceCountCommand.serviceCount = 0;
                FirstAidEventHandler.timeMilliesOnFirstAidReceipt = 0;
                TodoListCommand.todolist = Collections.emptyList();
                CoordlistCommand.coordlist = Collections.emptyList();
                HouseBankEventHandler.houseBanks = Collections.emptyList();
                EquipEventHandler.equipLogEntryList = Collections.emptyList();
                CarOpenModule.info = "";
                return;
            }

            Data data = g.fromJson(jsonData, Data.class);
            BankMoneyModule.bankBalance = data.getBankBalance();
            CashMoneyModule.cashBalance = data.getCashBalance();
            JobModule.jobBalance = data.getJobBalance();
            JobModule.jobExperience = data.getJobExperience();
            PayDayModule.currentTime = data.getPayDayTime();
            ServiceCountCommand.serviceCount = data.getServiceCount();
            FirstAidEventHandler.timeMilliesOnFirstAidReceipt = data.getFirstAidDate();
            TodoListCommand.todolist = data.getTodolist();
            CoordlistCommand.coordlist = data.getCoordlist();
            HouseBankEventHandler.houseBanks = data.getHouseBanks();
            EquipEventHandler.equipLogEntryList = data.getEquipList();
            CarOpenModule.info = data.getCarInfo() == null ? Strings.EMPTY : data.getCarInfo();

            for (HouseBankEntry houseBankEntry : HouseBankEventHandler.houseBanks)
                HouseBankEntry.houseNumbers.add(houseBankEntry.getHouseNumber());

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
            if (dataFile == null) return;
            Gson g = new Gson();
            Data data = new Data();
            data.setBankBalance(BankMoneyModule.bankBalance);
            data.setCashBalance(CashMoneyModule.cashBalance);
            data.setJobBalance(JobModule.jobBalance);
            data.setJobExperience(JobModule.jobExperience);
            data.setPayDayTime(PayDayModule.currentTime);
            data.setFirstAidDate(FirstAidEventHandler.timeMilliesOnFirstAidReceipt);
            data.setTodolist(TodoListCommand.todolist);
            data.setCoordlist(CoordlistCommand.coordlist);
            data.setHouseBanks(HouseBankEventHandler.houseBanks);
            data.setEquipList(EquipEventHandler.equipLogEntryList);
            data.setCarInfo(CarOpenModule.info);
            data.setServiceCount(ServiceCountCommand.serviceCount);
            FileUtils.writeStringToFile(dataFile, g.toJson(data), StandardCharsets.UTF_8.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}