package com.practice.echo.main;

import com.practice.NettyConfig;
import com.practice.echo.EchoNettyClient;

public class MainClient {

    public static void main(String[] args) {
        EchoNettyClient client = new EchoNettyClient(NettyConfig.PORT, NettyConfig.HOST);
        client.runClient();
    }
}
