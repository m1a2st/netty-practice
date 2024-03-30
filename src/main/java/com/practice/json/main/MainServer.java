package com.practice.json.main;

import com.practice.json.JsonServer;

public class MainServer {

    public static void main(String[] args) {
        JsonServer jsonServer = new JsonServer(6666);
        jsonServer.runServer();
    }
}
