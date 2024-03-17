package com.practice.discard.main;

import com.practice.discard.DiscardNettyServer;

public class ServerRunner {
    public static void main(String[] args) {

        new DiscardNettyServer(6000).runServer();
    }
}
