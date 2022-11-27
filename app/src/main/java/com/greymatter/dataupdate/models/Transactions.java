package com.greymatter.dataupdate.models;

public class Transactions {

    private String name,balance,amount,remarks,date;

    public Transactions(String name, String balance, String amount, String remarks, String date) {
        this.name = name;
        this.balance = balance;
        this.amount = amount;
        this.remarks = remarks;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
