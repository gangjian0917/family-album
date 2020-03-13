package com.vbeesoft.familyalbum.common.json;


import com.vbeesoft.familyalbum.common.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class JsonField {

    public static Integer getInteger(String json, String field) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        Map<String, Object> map = Json.toMap(json, Object.class);
        Integer num = (Integer) map.get(field);
        return num;
    }

    public static String getString(String json, String field) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        Map<String, Object> map = Json.toMap(json, Object.class);
        String str = (String) map.get(field);
        return str;
    }

    public static List<String> getStrings(Map<String, String> map, String field) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            String json = entry.getValue();
            String value = getString(json, field);
            list.add(value);
        }
        return list;
    }
}
