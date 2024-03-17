package com.practice.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import static io.netty.channel.ChannelOption.SO_KEEPALIVE;

@Slf4j
public class EchoNettyServer {

    private final int serverPort;
    private final ServerBootstrap serverBootstrap = new ServerBootstrap();

    public EchoNettyServer(int serverPort) {
        this.serverPort = serverPort;
    }

    public void runServer() {
        NioEventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {
            // 1. 創建反應器輪詢組
            serverBootstrap.group(bossLoopGroup, workerLoopGroup);
            // 2. 設置 NIO 類型的通道
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 3. 設置監聽端口
            serverBootstrap.localAddress(serverPort);
            // 4. 設置通道的參數
            serverBootstrap.option(SO_KEEPALIVE, true);
            // 5. 裝配子通道流水線
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(EchoNettyServerHandler.INSTANCE);
                }
            });
            // 6. 開始綁訂服務器
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            log.info("服務器啟動成功，監聽端口: {}", channelFuture.channel().localAddress());
            // 7. 關閉通道(並不是真正意義上關閉，而是註冊關閉事件，當通道關閉時才會真正關閉)
            // 這裡會進行阻塞，等待通道關閉
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (InterruptedException e) {
            log.error("服務器運行出現異常", e);
        } finally {
            // 8. 關閉 EventLoopGroup
            bossLoopGroup.shutdownGracefully();
            workerLoopGroup.shutdownGracefully();
        }
    }
}
