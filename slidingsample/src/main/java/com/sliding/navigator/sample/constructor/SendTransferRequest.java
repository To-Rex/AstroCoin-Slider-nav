package com.sliding.navigator.sample.constructor;

public class SendTransferRequest {
    String wallet_to;
    double amount;
    String comment;
    String type;
    String title;

    public String getWallet_to() {
        return wallet_to;
    }

    public void setWallet_to(String wallet_to) {
        this.wallet_to = wallet_to;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
