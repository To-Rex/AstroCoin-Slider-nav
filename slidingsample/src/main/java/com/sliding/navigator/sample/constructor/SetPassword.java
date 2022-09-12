package com.sliding.navigator.sample.constructor;

public class SetPassword {
    String old_password;
    String password;
    String password_confirmation;

    public SetPassword(String old_password, String password, String password_confirmation) {
        this.old_password = old_password;
        this.password = password;
        this.password_confirmation = password_confirmation;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }
}
