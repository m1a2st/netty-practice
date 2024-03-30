package com.practice.json.main;

import com.practice.json.JsonClient;

public class MainClient {

    public static void main(String[] args) {
        JsonClient jsonClient = new JsonClient(6666, "127.0.0.1");
        jsonClient.runClient();
    }
}
