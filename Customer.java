package com.bankingsystem.model;

public class Customer {

    private int accountId;
    private String customerName;
    private String phone;
    private String email;
    private double balance;

    public Customer() {
    }

    public Customer(String customerName, String phone, String email) {

        this.customerName = customerName;
        this.phone = phone;
        this.email = email;
        this.balance = 0.0;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {

        return "Account ID : " + accountId
                + "\nName : " + customerName
                + "\nPhone : " + phone
                + "\nEmail : " + email
                + "\nBalance : " + balance;
    }
}