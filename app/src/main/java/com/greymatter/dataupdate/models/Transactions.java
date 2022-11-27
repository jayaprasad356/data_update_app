package com.greymatter.dataupdate.models;

public class Transactions {

    private String TransactionName;
    private String TransactionDate;
    private String TransactionAmount;


    public Transactions(String transactionName, String transactionDate, String transactionAmount) {
        TransactionName = transactionName;
        TransactionDate = transactionDate;
        TransactionAmount = transactionAmount;
    }


    public String getTransactionName() {
        return TransactionName;
    }

    public void setTransactionName(String transactionName) {
        TransactionName = transactionName;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        TransactionDate = transactionDate;
    }

    public String getTransactionAmount() {
        return TransactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        TransactionAmount = transactionAmount;
    }
}
