package com.rettichlp.unicacityaddon.base.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {

    private int bankBalance;
    private int cashBalance;
    private int jobBalance;
    private int jobExperience;
    private int payDayTime;
    private int serviceCount;
    private long firstAidDate;
    private List<TodolistEntry> todolist;
    private List<CoordlistEntry> coordlist;
    private Map<Integer, HouseDataEntry> houseData;
    private List<EquipLogEntry> equipList;
    private String carInfo;

    public Data() {
    }

    public int getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(int bankBalance) {
        this.bankBalance = bankBalance;
    }

    public int getJobBalance() {
        return jobBalance;
    }

    public void setJobBalance(int jobBalance) {
        this.jobBalance = jobBalance;
    }

    public int getJobExperience() {
        return jobExperience;
    }

    public void setJobExperience(int jobExperience) {
        this.jobExperience = jobExperience;
    }

    public int getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(int cashBalance) {
        this.cashBalance = cashBalance;
    }

    public int getPayDayTime() {
        return payDayTime;
    }

    public void setPayDayTime(int payDayTime) {
        this.payDayTime = payDayTime;
    }

    public int getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(int serviceCount) {
        this.serviceCount = serviceCount;
    }

    public long getFirstAidDate() {
        return firstAidDate;
    }

    public void setFirstAidDate(long firstAidDate) {
        this.firstAidDate = firstAidDate;
    }

    public List<TodolistEntry> getTodolist() {
        return todolist == null ? new ArrayList<>() : todolist;
    }

    public void setTodolist(List<TodolistEntry> todolist) {
        this.todolist = todolist;
    }

    public List<CoordlistEntry> getCoordlist() {
        return coordlist == null ? new ArrayList<>() : coordlist;
    }

    public void setCoordlist(List<CoordlistEntry> coordlist) {
        this.coordlist = coordlist;
    }

    public Map<Integer, HouseDataEntry> getHouseData() {
        return houseData == null ? new HashMap<>() : houseData;
    }

    public void setHouseData(Map<Integer, HouseDataEntry> houseData) {
        this.houseData = houseData;
    }

    public List<EquipLogEntry> getEquipList() {
        return equipList == null ? new ArrayList<>() : equipList;
    }

    public void setEquipList(List<EquipLogEntry> equipList) {
        this.equipList = equipList;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }
}