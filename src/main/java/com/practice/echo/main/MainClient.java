package com.practice.echo.main;

import com.practice.echo.EchoNettyClient;

public class MainClient {

    public static void main(String[] args) {
        EchoNettyClient client = new EchoNettyClient(6000, "127.0.0.1");
        client.runClient();
    }
}
