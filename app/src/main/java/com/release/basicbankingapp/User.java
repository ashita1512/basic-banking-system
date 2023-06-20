package com.release.basicbankingapp;

public class User {

    private String userName;
    private String balance;

    public User(String userName, String balance) {
        this.userName = userName;
        this.balance = balance;
    }

    public String getUserName() {
        return userName;
    }

    public String getBalance() {
        return balance;
    }
}
