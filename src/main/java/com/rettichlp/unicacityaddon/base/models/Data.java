package com.rettichlp.unicacityaddon.base.models;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import joptsimple.internal.Strings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Data {

    @Builder.Default
    private int bankBalance = 0;

    @Builder.Default
    private String carInfo = Strings.EMPTY;

    @Builder.Default
    private int cashBalance = 0;

    @Builder.Default
    private List<CoordlistEntry> coordlist = Collections.emptyList();

    @Builder.Default
    private List<EquipLogEntry> equipList = Collections.emptyList();

    @Builder.Default
    private long firstAidDate = 0;

    @Builder.Default
    private Map<Integer, HouseData> houseData = new HashMap<>();

    @Builder.Default
    private int jobBalance = 0;

    @Builder.Default
    private int jobExperience = 0;

    @Builder.Default
    private int payDayTime = 0;

    @Builder.Default
    private long plantFertilizeTime = -1;

    @Builder.Default
    private long plantWaterTime = -1;

    @Builder.Default
    private int serviceCount = 0;

    @Builder.Default
    private List<TodolistEntry> todolist = Collections.emptyList();

    /**
     * Adds the given value <code>i</code> to the <code>bankBalance</code>
     */
    public void addBankBalance(int i) {
        bankBalance += i;
    }

    /**
     * Removes the given value <code>i</code> from the <code>bankBalance</code>
     */
    public void removeBankBalance(int i) {
        bankBalance -= i;
    }

    /**
     * Adds the given value <code>i</code> to the <code>cashBalance</code>
     */
    public void addCashBalance(int i) {
        cashBalance += i;
    }

    /**
     * Removes the given value <code>i</code> from the <code>cashBalance</code>
     */
    public void removeCashBalance(int i) {
        cashBalance -= i;
    }

    /**
     * Adds the given value <code>i</code> to the <code>jobBalance</code>
     */
    public void addJobBalance(int i) {
        jobBalance += i;
    }

    /**
     * Adds the given value <code>i</code> to the <code>jobExperience</code>
     */
    public void addJobExperience(int i) {
        jobExperience += i;
    }

    /**
     * Adds the given value <code>i</code> to the <code>payDayTime</code> and sends reminder message if necessary
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
     */
    public void addServiceCount(int i) {
        serviceCount += i;
    }

    /**
     * Removes the given value <code>i</code> from the <code>serviceCount</code>
     */
    public void removeServiceCount(int i) {
        if (serviceCount > 0)
            serviceCount -= i;
    }
}