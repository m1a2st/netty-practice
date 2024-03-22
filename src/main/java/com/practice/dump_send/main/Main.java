package com.practice.dump_send.main;

import com.practice.NettyConfig;
import com.practice.dump_send.NettyDumpSendClient;

public class Main {

    public static void main(String[] args) {
        NettyDumpSendClient client = new NettyDumpSendClient(NettyConfig.PORT, NettyConfig.HOST);
        client.runClient();
    }
}
