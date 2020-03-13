package com.vbeesoft.familyalbum.model;

import java.io.Serializable;
import java.util.List;

public class ResultBean implements Serializable {
    public static long serialVersionUID = 0;
    /**
     * date : 2019-12-07
     */

    private String date;
    private List<String> imgUrls;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "date='" + date + '\'' +
                ", imgUrls=" + imgUrls +
                '}';
    }
}
