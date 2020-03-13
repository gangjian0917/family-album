package com.vbeesoft.familyalbum.model.dataobject;


import java.io.Serializable;

public class UserDO implements Serializable {

    public static long serialVersionUID = 0;

    private String account;
    private String password;
    private String phoneNumber;
    private String userID;

    public UserDO() {
        super();
    }

    public UserDO(String account, String password, String phoneNumber, String userID) {
        this.account = account;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
