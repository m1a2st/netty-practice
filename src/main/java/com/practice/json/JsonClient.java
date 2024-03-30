package com.practice.json;

import com.practice.echo.EchoNettyClientHandler;
import com.practice.object.JsonMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static java.time.LocalDateTime.now;

@Slf4j
public class JsonClient {

    private final static String CONTENT = "Hello, World!";
    private final int serverPort;
    private final String serverIp;
    private final Bootstrap bootstrap = new Bootstrap();

    public JsonClient(int serverPort, String serverIp) {
        this.serverPort = serverPort;
        this.serverIp = serverIp;
    }

    public void runClient() {
        NioEventLoopGroup workLoopGroup = new NioEventLoopGroup();
        try {
            // 1. 創建反應器輪詢組
            bootstrap.group(workLoopGroup);
            // 2. 設置 NIO 類型的通道
            bootstrap.channel(NioSocketChannel.class);
            // 3. 設置監聽端口
            bootstrap.remoteAddress(serverIp, serverPort);
            // 4. 設置通道的參數
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            // 5. 裝配子通道流水線
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LengthFieldPrepender(4));
                    ch.pipeline().addLast(new StringEncoder(StandardCharsets.UTF_8));
                }
            });

            ChannelFuture channelFuture = bootstrap.connect();
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    log.info("客戶端連接成功");
                } else {
                    log.error("客戶端連接失敗");
                }
            });
            // 阻塞，直到連接成功
            channelFuture.sync();
            Channel channel = channelFuture.channel();
            for (int i = 0; i < 1000; i++) {
                JsonMessage jsonMessage = new JsonMessage(i, i + " -> " + CONTENT);
                channel.writeAndFlush(jsonMessage.convert2Json());
                log.info("客戶端發送一個JsonMessage: {}", jsonMessage);
            }
            channel.flush();
            channelFuture = channel.closeFuture();
            channelFuture.sync();
        } catch (InterruptedException e) {
            log.error("客戶端運行出現異常", e);
        } finally {
            // 7. 關閉 EventLoopGroup
            workLoopGroup.shutdownGracefully();
        }
    }
}
