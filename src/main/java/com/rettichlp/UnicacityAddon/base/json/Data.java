package com.rettichlp.UnicacityAddon.base.json;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private int bankBalance;
    private int cashBalance;
    private int jobBalance;
    private int jobExperience;
    private int payDayTime;
    private int serviceCount;
    private int deaths;
    private int kills;
    private List<TodolistEntry> todolist;
    private List<CoordlistEntry> coordlist;
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

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
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

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }
}