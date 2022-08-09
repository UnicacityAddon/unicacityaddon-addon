package com.rettichlp.UnicacityAddon.base.json.balance;

import com.rettichlp.UnicacityAddon.base.json.todo.Todolist;

public class Offlinedata {

    private int bankBalance;
    private int cashBalance;
    private int jobBalance;
    private int paydayTime;
    private Todolist todolist;

    public Offlinedata() {
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

    public Todolist getTodolist() {
        return todolist;
    }

    public void setTodolist(Todolist todolist) {
        this.todolist = todolist;
    }
}
