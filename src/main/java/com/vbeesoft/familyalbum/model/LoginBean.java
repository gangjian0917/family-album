package com.vbeesoft.familyalbum.model;

import java.io.Serializable;

public class LoginBean implements Serializable {
    public static long serialVersionUID = 0;

    /**
     * result : 101
     */

    private int result;
    /**
     * userID : fltw5
     */

    private String userID;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "result=" + result +
                ", userID='" + userID + '\'' +
                '}';
    }
}
