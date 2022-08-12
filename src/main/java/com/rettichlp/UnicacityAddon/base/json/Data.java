package com.rettichlp.UnicacityAddon.base.json;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private int bankBalance;
    private int cashBalance;
    private int jobBalance;
    private int payDayTime;
    private List<TodolistEntry> todolist;

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

    public List<TodolistEntry> getTodolist() {
        return todolist == null ? new ArrayList<>() : todolist;
    }

    public void setTodolist(List<TodolistEntry> todolist) {
        this.todolist = todolist;
    }
}