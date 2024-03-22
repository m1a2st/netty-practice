package com.practice.discard.main;

import com.practice.NettyConfig;
import com.practice.discard.DiscardNettyServer;

public class ServerRunner {
    public static void main(String[] args) {

        new DiscardNettyServer(NettyConfig.PORT).runServer();
    }
}
