package com.rettichlp.unicacityaddon.base.models;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Equip;
import joptsimple.internal.Strings;
import lombok.Setter;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
public class Data {

    private Integer bankBalance;
    private String carInfo;
    private Integer cashBalance;
    private Map<DrugType, Map<DrugPurity, Integer>> drugInventoryMap;
    private List<CoordlistEntry> coordlist;
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

    public Data() {
    }

    public int getBankBalance() {
        return bankBalance != null ? bankBalance : 0;
    }

    public String getCarInfo() {
        return carInfo != null ? carInfo : Strings.EMPTY;
    }

    public int getCashBalance() {
        return cashBalance != null ? cashBalance : 0;
    }

    public Map<DrugType, Map<DrugPurity, Integer>> getDrugInventoryMap() {
        return drugInventoryMap != null ? drugInventoryMap : Collections.emptyMap();
    }

    public List<CoordlistEntry> getCoordlist() {
        return coordlist != null ? coordlist : Collections.emptyList();
    }

    public Map<Equip, Integer> getEquipMap() {
        return equipMap != null ? equipMap : Collections.emptyMap();
    }

    public long getFirstAidDate() {
        return firstAidDate != null ? firstAidDate : 0;
    }

    public Map<Integer, HouseData> getHouseDataMap() {
        return houseDataMap != null ? houseDataMap : Collections.emptyMap();
    }

    public int getJobBalance() {
        return jobBalance != null ? jobBalance : 0;
    }

    public int getJobExperience() {
        return jobExperience != null ? jobExperience : 0;
    }

    public int getPayDayTime() {
        return payDayTime != null ? payDayTime : 0;
    }

    public long getPlantFertilizeTime() {
        return plantFertilizeTime != null ? plantFertilizeTime : 0;
    }

    public long getPlantWaterTime() {
        return plantWaterTime != null ? plantWaterTime : 0;
    }

    public int getServiceCount() {
        return serviceCount != null ? serviceCount : 0;
    }

    public int getTimer() {
        return timer != null ? timer : 0;
    }

    public List<TodolistEntry> getTodolist() {
        return todolist != null ? todolist : Collections.emptyList();
    }

    /**
     * Adds the given value <code>i</code> to the <code>bankBalance</code>
     *
     * @param i Amount of money to be added to the <code>bankBalance</code>
     */
    public void addBankBalance(int i) {
        bankBalance += i;
    }

    /**
     * Removes the given value <code>i</code> from the <code>bankBalance</code>
     *
     * @param i Amount of money to be removed from the <code>bankBalance</code>
     */
    public void removeBankBalance(int i) {
        bankBalance -= i;
    }

    /**
     * Adds the given value <code>i</code> to the <code>cashBalance</code>
     *
     * @param i Amount of money to be added to the <code>cashBalance</code>
     */
    public void addCashBalance(int i) {
        cashBalance += i;
    }

    /**
     * Removes the given value <code>i</code> from the <code>cashBalance</code>
     *
     * @param i Amount of money to be removed from the <code>cashBalance</code>
     */
    public void removeCashBalance(int i) {
        cashBalance -= i;
    }

    /**
     * Adds the position of the <code>UPlayer</code> with the given name to the <code>coordlist</code>
     *
     * @param name     Name of the position to be added to the <code>coordlist</code>
     * @param blockPos Position to be added to the <code>coordlist</code>
     * @see BlockPos
     * @see UPlayer
     */
    public boolean addCoordToCoordlist(String name, BlockPos blockPos) {
        return coordlist.add(new CoordlistEntry(name, blockPos.getX(), blockPos.getY(), blockPos.getZ()));
    }

    /**
     * Removes the position with the given name from the <code>coordlist</code>
     *
     * @param name Name of the position to be removed from the <code>coordlist</code>
     */
    public boolean removeCoordFromCoordlist(String name) {
        return coordlist.removeIf(coordlistEntry -> coordlistEntry.getName().equalsIgnoreCase(name));
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
        UnicacityAddon.LOGGER.info("DrugInventoryInteraction: Added amount {} of DrugType {} with DrugPurity {} to inventory", amount, drugType, drugPurity);
        if (drugType != null) {
            Map<DrugPurity, Integer> drugPurityIntegerMap = drugInventoryMap.getOrDefault(drugType, new HashMap<>());
            int oldAmount = drugPurityIntegerMap.getOrDefault(drugPurity, 0);
            drugPurityIntegerMap.put(drugPurity, oldAmount + amount);
            drugInventoryMap.put(drugType, drugPurityIntegerMap);
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
        UnicacityAddon.LOGGER.info("DrugInventoryInteraction: Removed amount {} of DrugType {} with DrugPurity {} from inventory", amount, drugType, drugPurity);
        if (drugType != null) {
            Map<DrugPurity, Integer> drugPurityIntegerMap = drugInventoryMap.getOrDefault(drugType, new HashMap<>());
            int oldAmount = drugPurityIntegerMap.getOrDefault(drugPurity, 0);
            drugPurityIntegerMap.put(drugPurity, Math.max(oldAmount - amount, 0));
            drugInventoryMap.put(drugType, drugPurityIntegerMap);
        }
    }

    /**
     * Adds the given {@link Equip} to the <code>equipMap</code>
     *
     * @param equip {@link Equip} to be added to the <code>equipMap</code>
     * @see Equip
     */
    public void addEquipToEquipMap(Equip equip) {
        int newEquipAmount = equipMap.getOrDefault(equip, 0) + 1;
        equipMap.put(equip, newEquipAmount);
    }

    /**
     * Adds the given value <code>i</code> to the <code>jobBalance</code>
     *
     * @param i Amount of money to be added to the <code>jobBalance</code>
     */
    public void addJobBalance(int i) {
        jobBalance += i;
    }

    /**
     * Adds the given value <code>i</code> to the <code>jobExperience</code>
     *
     * @param i Amount of experience to be added to the <code>jobExperience</code>
     */
    public void addJobExperience(int i) {
        jobExperience += i;
    }

    /**
     * Adds the given value <code>i</code> to the <code>payDayTime</code> and sends reminder message if necessary
     *
     * @param i Amount of minutes to be added to the <code>jobExperience</code>
     */
    public void addPayDayTime(int i) {
        UPlayer p = AbstractionLayer.getPlayer();
        switch (payDayTime += i) {
            case 55:
                p.sendInfoMessage("Du hast in 5 Minuten deinen PayDay.");
                break;
            case 57:
                p.sendInfoMessage("Du hast in 3 Minuten deinen PayDay.");
                break;
            case 59:
                p.sendInfoMessage("Du hast in 1 Minute deinen PayDay.");
                break;
        }
    }

    /**
     * Adds the given value <code>i</code> to the <code>serviceCount</code>
     *
     * @param i Amount of services to be added to the <code>serviceCount</code>
     */
    public void addServiceCount(int i) {
        serviceCount += i;
    }

    /**
     * Removes the given value <code>i</code> from the <code>serviceCount</code>
     *
     * @param i Amount of services to be removed from the <code>serviceCount</code>
     */
    public void removeServiceCount(int i) {
        if (serviceCount > 0)
            serviceCount -= i;
    }
}