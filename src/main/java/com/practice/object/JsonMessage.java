package com.practice.object;

import com.practice.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonMessage {

    private int id;
    private String content;

    public String convert2Json() {
        return JsonUtil.pojo2Json(this);
    }

    public static JsonMessage convert2Object(String json) {
        return JsonUtil.json2Pojo(json, JsonMessage.class);
    }
}
