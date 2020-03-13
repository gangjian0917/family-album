package com.vbeesoft.familyalbum.model;

import java.io.Serializable;
import java.util.List;

public class VideoSnapshootBean extends BaseCode implements Serializable {
    public static long serialVersionUID = 0;

    private String snapUrl;


    //0-缓存 1-实时计算
    private int cache = in_cache;

    public final static int in_cache = 0;
    public final static int calculate_now = 1;

    public String getSnapUrl() {
        return snapUrl;
    }

    public void setSnapUrl(String snapUrl) {
        this.snapUrl = snapUrl;
    }

    public int getCache() {
        return cache;
    }

    public void setCache(int cache) {
        this.cache = cache;
    }

    @Override
    public String toString() {
        return "VideoSnapshootBean{" +
                "snapUrl='" + snapUrl + '\'' +
                ", cache=" + cache +
                '}';
    }
}
