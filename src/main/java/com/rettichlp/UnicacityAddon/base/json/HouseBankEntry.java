package com.rettichlp.UnicacityAddon.base.json;

import java.util.ArrayList;
import java.util.List;

public class HouseBankEntry {
    private final int houseNumber;
    private int value;
    public static List<Integer> houseNumbers = new ArrayList<>();

    public HouseBankEntry(int houseNumber, int value) {
        HouseBankEntry.houseNumbers.add(houseNumber);

        this.houseNumber = houseNumber;
        this.value = value;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int setAmount) {
        this.value = setAmount;
    }

    public void removeValue(int removeAmount) {
        this.value -= removeAmount;
    }

    public void addValue(int addAmount) {
        this.value += addAmount;
    }
}
