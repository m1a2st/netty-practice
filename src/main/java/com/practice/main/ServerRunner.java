package com.practice.main;

import com.practice.DiscardNettyServer;

public class ServerRunner {
    public static void main(String[] args) {

        new DiscardNettyServer(6000).runServer();
    }
}
