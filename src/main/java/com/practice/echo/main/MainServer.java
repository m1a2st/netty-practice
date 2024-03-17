package com.practice.echo.main;

import com.practice.echo.EchoNettyServer;

public class MainServer {

    public static void main(String[] args) {
        new EchoNettyServer(6000).runServer();
    }
}
