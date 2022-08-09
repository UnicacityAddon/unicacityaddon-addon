package com.rettichlp.UnicacityAddon.base.json;

import java.util.List;

public class Data {

    private int bankBalance;
    private int cashBalance;
    private int jobBalance;
    private int paydayTime;
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

    public int getPaydayTime() {
        return paydayTime;
    }

    public void setPaydayTime(int paydayTime) {
        this.paydayTime = paydayTime;
    }

    public List<TodolistEntry> getTodolist() {
        return todolist;
    }

    public void setTodolist(List<TodolistEntry> todolist) {
        this.todolist = todolist;
    }
}