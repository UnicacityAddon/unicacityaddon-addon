package com.rettichlp.UnicacityAddon.base.json.balance;

public class Offlinedata {

    private int bankBalance;
    private int cash;
    private int jobBalance;
    private int paydayTime;

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
        return cash;
    }

    public void setCashBalance(int cash) {
        this.cash = cash;
    }

    public int getPaydayTime() {
        return paydayTime;
    }

    public void setPaydayTime(int paydayTime) {
        this.paydayTime = paydayTime;
    }
}
