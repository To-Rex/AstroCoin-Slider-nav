package com.sliding.navigator.sample.constructor;

public class LoginResponse {
    private String token;
    private String name;
    private String last_name;

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

    public String getStatus() {
        return token;
    }

    public void setStatus(String status) {
        this.token = status;
    }
}
