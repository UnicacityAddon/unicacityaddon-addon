package com.rettichlp.unicacityaddon.base.io.file;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Equip;
import com.rettichlp.unicacityaddon.base.events.OfflineDataChangedEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author RettichLP
 */
public class Data {

    private List<Armament> armamentList;
    private Integer bankBalance;
    private Boolean carOpen;
    private Integer cashBalance;
    private List<CoordlistEntry> coordlist;
    private Map<DrugType, Map<DrugPurity, Integer>> drugInventoryMap;
    private Map<Equip, Integer> equipMap;
    private Long firstAidDate;
    private Map<Integer, HouseData> houseDataMap;
    private Integer jobBalance;
    private Integer jobExperience;
    private Integer payDayTime;
    private Long plantFertilizeTime;
    private Long plantWaterTime;
    private Integer serviceCount;
    private Integer timer;
    private List<TodolistEntry> todolist;

    // has to be static, so it will not be overwritten by gson data loading
    private static UnicacityAddon unicacityAddon;

    public Data(UnicacityAddon unicacityAddon) {
        Data.unicacityAddon = unicacityAddon;
    }

    public List<Armament> getArmamentList() {
        return Optional.ofNullable(armamentList).orElse(new ArrayList<>());
    }

    public int getBankBalance() {
        return Optional.ofNullable(bankBalance).orElse(0);
    }

    public void setBankBalance(Integer bankBalance) {
        this.bankBalance = bankBalance;
        saveAndFireEvent();
    }

    public boolean isCarOpen() {
        return Optional.ofNullable(carOpen).orElse(false);
    }

    public void setCarOpen(boolean carOpen) {
        this.carOpen = carOpen;
        saveAndFireEvent();
    }

    public int getCashBalance() {
        return Optional.ofNullable(cashBalance).orElse(0);
    }

    public void setCashBalance(Integer cashBalance) {
        this.cashBalance = cashBalance;
        saveAndFireEvent();
    }

    public List<CoordlistEntry> getCoordlist() {
        return Optional.ofNullable(coordlist).orElse(new ArrayList<>());
    }

    public Map<DrugType, Map<DrugPurity, Integer>> getDrugInventoryMap() {
        return Optional.ofNullable(drugInventoryMap).orElse(new HashMap<>());
    }

    public void setDrugInventoryMap(Map<DrugType, Map<DrugPurity, Integer>> drugInventoryMap) {
        this.drugInventoryMap = drugInventoryMap;
        saveAndFireEvent();
    }

    public Map<Equip, Integer> getEquipMap() {
        return Optional.ofNullable(equipMap).orElse(new HashMap<>());
    }

    public void setEquipMap(Map<Equip, Integer> equipMap) {
        this.equipMap = equipMap;
        saveAndFireEvent();
    }

    public long getFirstAidDate() {
        return Optional.ofNullable(firstAidDate).orElse(0L);
    }

    public void setFirstAidDate(Long firstAidDate) {
        this.firstAidDate = firstAidDate;
        saveAndFireEvent();
    }

    public Map<Integer, HouseData> getHouseDataMap() {
        return Optional.ofNullable(houseDataMap).orElse(new HashMap<>());
    }

    public int getJobBalance() {
        return Optional.ofNullable(jobBalance).orElse(0);
    }

    public void setJobBalance(Integer jobBalance) {
        this.jobBalance = jobBalance;
        saveAndFireEvent();
    }

    public int getJobExperience() {
        return Optional.ofNullable(jobExperience).orElse(0);
    }

    public void setJobExperience(Integer jobExperience) {
        this.jobExperience = jobExperience;
        saveAndFireEvent();
    }

    public int getPayDayTime() {
        return Optional.ofNullable(payDayTime).orElse(0);
    }

    public void setPayDayTime(Integer payDayTime) {
        this.payDayTime = payDayTime;
        saveAndFireEvent();
    }

    public long getPlantFertilizeTime() {
        return Optional.ofNullable(plantFertilizeTime).orElse(0L);
    }

    public void setPlantFertilizeTime(Long plantFertilizeTime) {
        this.plantFertilizeTime = plantFertilizeTime;
        saveAndFireEvent();
    }

    public long getPlantWaterTime() {
        return Optional.ofNullable(plantWaterTime).orElse(0L);
    }

    public void setPlantWaterTime(Long plantWaterTime) {
        this.plantWaterTime = plantWaterTime;
        saveAndFireEvent();
    }

    public int getServiceCount() {
        return Optional.ofNullable(serviceCount).orElse(0);
    }

    public void setServiceCount(Integer serviceCount) {
        this.serviceCount = serviceCount;
        saveAndFireEvent();
    }

    public int getTimer() {
        return Optional.ofNullable(timer).orElse(0);
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
        saveAndFireEvent();
    }

    public List<TodolistEntry> getTodolist() {
        return Optional.ofNullable(todolist).orElse(new ArrayList<>());
    }

    /**
     * Adds a <code>Armament</code> object, created by the given values, to the <code>armamentList</code>
     *
     * @param name   name of the <code>Armament</code> pattern
     * @param weapon {@link Weapon} of the <code>Armament</code> pattern
     * @param amount amount of ammunition of the <code>Armament</code> pattern
     * @see Armament
     * @see Weapon
     */
    public void addArmamentPattern(String name, Weapon weapon, int amount) {
        List<Armament> newArmamentList = getArmamentList();
        newArmamentList.add(new Armament(name, weapon, amount));
        armamentList = newArmamentList;
        saveAndFireEvent();
    }

    /**
     * Removes the <code>Armament</code> object with the given name of the <code>armamentList</code>
     *
     * @param name name of the <code>Armament</code> pattern
     * @see Armament
     * @see Weapon
     */
    public boolean removeArmamentPattern(String name) {
        List<Armament> newArmamentList = getArmamentList();
        boolean success = newArmamentList.removeIf(armament -> armament.getName().equalsIgnoreCase(name));
        armamentList = newArmamentList;
        saveAndFireEvent();
        return success;
    }

    /**
     * Adds the given value <code>i</code> to the <code>bankBalance</code>
     *
     * @param i Amount of money to be added to the <code>bankBalance</code>
     */
    public void addBankBalance(int i) {
        bankBalance = getBankBalance() + i;
        saveAndFireEvent();
    }

    /**
     * Removes the given value <code>i</code> from the <code>bankBalance</code>
     *
     * @param i Amount of money to be removed from the <code>bankBalance</code>
     */
    public void removeBankBalance(int i) {
        bankBalance = getBankBalance() - i;
        saveAndFireEvent();
    }

    /**
     * Adds the given value <code>i</code> to the <code>cashBalance</code>
     *
     * @param i Amount of money to be added to the <code>cashBalance</code>
     */
    public void addCashBalance(int i) {
        cashBalance = getCashBalance() + i;
        saveAndFireEvent();
    }

    /**
     * Removes the given value <code>i</code> from the <code>cashBalance</code>
     *
     * @param i Amount of money to be removed from the <code>cashBalance</code>
     */
    public void removeCashBalance(int i) {
        cashBalance = getCashBalance() - i;
        saveAndFireEvent();
    }

    /**
     * Adds the location of the <code>AddonPlayer</code> with the given name to the <code>coordlist</code>
     *
     * @param name     Name of the location to be added to the <code>coordlist</code>
     * @param location Location to be added to the <code>coordlist</code>
     * @see FloatVector3
     * @see AddonPlayer
     */
    public void addCoordToCoordlist(String name, FloatVector3 location) {
        List<CoordlistEntry> newCoordlistEntryList = getCoordlist();
        newCoordlistEntryList.add(new CoordlistEntry(name, location.getX(), location.getY(), location.getZ()));
        coordlist = newCoordlistEntryList;
        saveAndFireEvent();
    }

    /**
     * Removes the location with the given name from the <code>coordlist</code>
     *
     * @param name Name of the location to be removed from the <code>coordlist</code>
     */
    public boolean removeCoordFromCoordlist(String name) {
        List<CoordlistEntry> newCoordlistEntryList = getCoordlist();
        boolean success = newCoordlistEntryList.removeIf(coordlistEntry -> coordlistEntry.getName().equalsIgnoreCase(name));
        coordlist = newCoordlistEntryList;
        saveAndFireEvent();
        return success;
    }

    /**
     * Adds the given <code>amount</code> of {@link DrugType} with its {@link DrugPurity} to the <code>drugInventoryMap</code>
     *
     * @param drugType   {@link DrugType} to be added to the <code>drugInventoryMap</code>
     * @param drugPurity {@link DrugPurity} of the {@link DrugType}
     * @param amount     Amount of the {@link DrugType}
     * @see DrugType
     * @see DrugPurity
     */
    public void addDrugToInventory(DrugType drugType, DrugPurity drugPurity, int amount) {
        unicacityAddon.logger().info("DrugInventoryInteraction: Added amount {} of DrugType {} with DrugPurity {} to inventory", amount, drugType, drugPurity);
        if (drugType != null) {
            Map<DrugPurity, Integer> drugPurityIntegerMap = getDrugInventoryMap().getOrDefault(drugType, new HashMap<>());
            int oldAmount = drugPurityIntegerMap.getOrDefault(drugPurity, 0);
            drugPurityIntegerMap.put(drugPurity, oldAmount + amount);
            Map<DrugType, Map<DrugPurity, Integer>> newdrugInventoryMap = getDrugInventoryMap();
            newdrugInventoryMap.put(drugType, drugPurityIntegerMap);
            drugInventoryMap = newdrugInventoryMap;
            saveAndFireEvent();
        }
    }

    /**
     * Removes the given <code>amount</code> of {@link DrugType} with its {@link DrugPurity} from the <code>drugInventoryMap</code>
     *
     * @param drugType   {@link DrugType} to be removed from the <code>drugInventoryMap</code>
     * @param drugPurity {@link DrugPurity} of the {@link DrugType}
     * @param amount     Amount of the {@link DrugType}
     * @see DrugType
     * @see DrugPurity
     */
    public void removeDrugFromInventory(DrugType drugType, DrugPurity drugPurity, int amount) {
        unicacityAddon.logger().info("DrugInventoryInteraction: Removed amount {} of DrugType {} with DrugPurity {} from inventory", amount, drugType, drugPurity);
        if (drugType != null) {
            Map<DrugPurity, Integer> drugPurityIntegerMap = getDrugInventoryMap().getOrDefault(drugType, new HashMap<>());
            int oldAmount = drugPurityIntegerMap.getOrDefault(drugPurity, 0);
            drugPurityIntegerMap.put(drugPurity, Math.max(oldAmount - amount, 0));
            Map<DrugType, Map<DrugPurity, Integer>> newdrugInventoryMap = getDrugInventoryMap();
            newdrugInventoryMap.put(drugType, drugPurityIntegerMap);
            drugInventoryMap = newdrugInventoryMap;
            saveAndFireEvent();
        }
    }

    /**
     * Adds the given {@link Equip} to the <code>equipMap</code>
     *
     * @param equip {@link Equip} to be added to the <code>equipMap</code>
     * @see Equip
     */
    public void addEquipToEquipMap(Equip equip) {
        Map<Equip, Integer> newEquipMap = getEquipMap();
        newEquipMap.put(equip, newEquipMap.getOrDefault(equip, 0) + 1);
        equipMap = newEquipMap;
        saveAndFireEvent();
    }

    /**
     * Returns the {@link HouseData} of the given <code>houseNumber</code> or creates them
     *
     * @param houseNumber Number of the house, from which the {@link HouseData} needs to be extracted
     * @see HouseData
     */
    public HouseData getHouseData(int houseNumber) {
        HouseData houseData = getHouseDataMap().getOrDefault(houseNumber, new HouseData(houseNumber));
        updateHouseData(houseNumber, houseData);
        return houseData;
    }

    /**
     * Updates the <code>houseDataMap</code> with the given <code>houseNumber</code> and its {@link HouseData}
     *
     * @param houseNumber Number of the house to be updated
     * @param houseData   {@link HouseData} of the house
     * @see HouseData
     */
    public void updateHouseData(int houseNumber, HouseData houseData) {
        Map<Integer, HouseData> newHouseDataMap = getHouseDataMap();
        newHouseDataMap.put(houseNumber, houseData);
        houseDataMap = newHouseDataMap;
        saveAndFireEvent();
    }

    /**
     * Removes the <code>houseNumber</code> with its associated {@link HouseData} from the <code>houseDataMap</code>
     *
     * @param houseNumber Number of the house to be removed
     * @see HouseData
     */
    public void removeHouseData(int houseNumber) {
        Map<Integer, HouseData> newHouseDataMap = getHouseDataMap();
        newHouseDataMap.remove(houseNumber);
        houseDataMap = newHouseDataMap;
        saveAndFireEvent();
    }

    /**
     * Sends a message which contains information about all house banks of registered {@link HouseData}
     *
     * @see AddonPlayer
     * @see HouseData
     * @see Message
     */
    public void sendAllHouseBankMessage() {
        AddonPlayer p = unicacityAddon.player();
        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Hauskassen:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        getHouseDataMap().values().forEach(houseData -> p.sendMessage(houseData.getBankComponent()));
        p.sendEmptyMessage();
    }

    /**
     * Sends a message which contains information about all drug storages of registered {@link HouseData}
     *
     * @see AddonPlayer
     * @see HouseData
     * @see Message
     */
    public void sendAllDrugStorageMessage() {
        AddonPlayer p = unicacityAddon.player();
        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Drogenlager:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        getHouseDataMap().values().forEach(houseData -> p.sendMessage(houseData.getStorageComponent()));
        p.sendEmptyMessage();
    }

    /**
     * Adds the given value <code>i</code> to the <code>jobBalance</code>
     *
     * @param i Amount of money to be added to the <code>jobBalance</code>
     */
    public void addJobBalance(int i) {
        jobBalance = getJobBalance() + i;
        saveAndFireEvent();
    }

    /**
     * Adds the given value <code>i</code> to the <code>jobExperience</code>
     *
     * @param i Amount of experience to be added to the <code>jobExperience</code>
     */
    public void addJobExperience(int i) {
        jobExperience = getJobExperience() + i;
        saveAndFireEvent();
    }

    /**
     * Adds the given value <code>i</code> to the <code>payDayTime</code> and sends reminder message if necessary
     *
     * @param i Amount of minutes to be added to the <code>jobExperience</code>
     */
    public void addPayDayTime(int i) {
        payDayTime = getPayDayTime() + i;
        saveAndFireEvent();
    }

    /**
     * Adds the given value <code>i</code> to the <code>serviceCount</code>
     *
     * @param i Amount of services to be added to the <code>serviceCount</code>
     */
    public void addServiceCount(int i) {
        serviceCount = getServiceCount() + i;
        saveAndFireEvent();
    }

    private void saveAndFireEvent() {
        unicacityAddon.fileService().saveData();
        if (unicacityAddon.labyAPI().addonService().isEnabled("unicacityaddon")) {
            unicacityAddon.labyAPI().eventBus().fire(new OfflineDataChangedEvent(unicacityAddon.fileService().data()));
            unicacityAddon.utilService().debug("Triggered OfflineDataChangedEvent");
        }
    }
}