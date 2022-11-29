package com.greymatter.dataupdate.models;

public class Users {

    private String id,name,mobile,balance,expense;
    public Users(){

    }

    public Users(String id, String name, String mobile, String balance, String expense) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.balance = balance;
        this.expense = expense;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }
}
