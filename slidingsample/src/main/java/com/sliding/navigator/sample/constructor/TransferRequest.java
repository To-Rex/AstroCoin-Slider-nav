package com.sliding.navigator.sample.constructor;

public class TransferRequest {
    private String id;
    private String wallet_from;
    private String wallet_to;
    private String fio;
    private String amount;
    private String title;
    private String type;
    private String comment;
    private String status;
    private String date;
    private String timestamp;
    private String datatransfer;


    public TransferRequest(String id, String wallet_from, String wallet_to, String fio, String amount, String title, String type, String comment, String status, String date, String timestamp, String datatransfer) {

            this.id = id;
            this.wallet_from = wallet_from;
            this.wallet_to = wallet_to;
            this.fio = fio;
            this.amount = amount;
            this.title = title;
            this.type = type;
            this.comment = comment;
            this.status = status;
            this.date = date;
            this.timestamp = timestamp;
            this.datatransfer = datatransfer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWallet_from() {
        return wallet_from;
    }

    public void setWallet_from(String wallet_from) {
        this.wallet_from = wallet_from;
    }

    public String getWallet_to() {
        return wallet_to;
    }

    public void setWallet_to(String wallet_to) {
        this.wallet_to = wallet_to;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDatatransfer() {
        return datatransfer;
    }

    public void setDatatransfer(String datatransfer) {
        this.datatransfer = datatransfer;
    }
}
