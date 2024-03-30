package com.practice.utils;

import com.google.gson.GsonBuilder;

public class JsonUtil {

    static GsonBuilder builder = new GsonBuilder();

    static {
        builder.disableHtmlEscaping();
    }

    public static String pojo2Json(Object obj) {
        return builder.create().toJson(obj);
    }

    public static <T> T json2Pojo(String json, Class<T> clazz) {
        return builder.create().fromJson(json, clazz);
    }
}
