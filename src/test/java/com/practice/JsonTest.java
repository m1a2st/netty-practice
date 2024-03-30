package com.practice;

import com.practice.object.JsonMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class JsonTest {

    @Test
    public void testJsonMessage() {
        JsonMessage jsonMessage = new JsonMessage();
        jsonMessage.setId(1);
        jsonMessage.setContent("Hello, World!");
        String json = jsonMessage.convert2Json();
        log.info(json);
        JsonMessage newJsonMessage = JsonMessage.convert2Object(json);
        log.info(newJsonMessage.toString());
    }
}
