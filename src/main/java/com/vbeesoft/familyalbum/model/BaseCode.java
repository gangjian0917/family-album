package com.vbeesoft.familyalbum.model;

public class BaseCode {
    private int code = 200;
    private Object value = "";
    private String message = "";
    private String directUrl = "";

    @Override
    public String toString() {
        return "BaseCode{" +
                "code=" + code +
                ", value=" + value +
                ", message='" + message + '\'' +
                ", directUrl='" + directUrl + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDirectUrl() {
        return directUrl;
    }

    public void setDirectUrl(String directUrl) {
        this.directUrl = directUrl;
    }
}
