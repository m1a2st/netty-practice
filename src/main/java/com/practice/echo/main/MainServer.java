package com.practice.echo.main;

import com.practice.NettyConfig;
import com.practice.echo.EchoNettyServer;

public class MainServer {

    public static void main(String[] args) {
        new EchoNettyServer(NettyConfig.PORT).runServer();
    }
}
