package com.sliding.navigator.sample.constructor;

public class UserRequestdata{

    private String id;
    private String name;
    private String last_name;
    private String stack;
    private String photo;
    private String qwasar;
    private String status;
    private String balance;
    private String wallet;

    public UserRequestdata(String id, String name, String last_name, String stack, String photo, String qwasar, String status, String balance, String wallet) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.stack = stack;
        this.photo = photo;
        this.qwasar = qwasar;
        this.status = status;
        this.balance = balance;
        this.wallet = wallet;
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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getQwasar() {
        return qwasar;
    }

    public void setQwasar(String qwasar) {
        this.qwasar = qwasar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }
}
