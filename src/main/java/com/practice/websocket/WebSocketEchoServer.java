package com.practice.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketEchoServer {

    static class EchoInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            // HTTP 請求解碼器
            pipeline.addLast(new HttpRequestDecoder());
            // HTTP 響應編碼器
            pipeline.addLast(new HttpResponseEncoder());
            // 將 HTTP 請求/響應消息的多個部分組合成一個完整的 HTTP 消息
            pipeline.addLast(new HttpObjectAggregator(65535));
            // WebSocket 協議處理器，配置 WebSocket 監聽的 URI 路徑，協議包長度限制
            pipeline.addLast(new WebSocketServerProtocolHandler("/ws", "echo", true, 10 *1024));
            pipeline.addLast(new WebPageHandler());
            pipeline.addLast(new TextWebSocketFrameHandler());
        }
    }

    public static void main(String ip) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new EchoInitializer());
            Channel channel = serverBootstrap.bind(18999).sync().channel();
            log.info("Open your web browser and navigate to http://{}:{}/",ip, 18999);
            channel.closeFuture().syncUninterruptibly();
        } catch (InterruptedException e) {
            log.error("Error occurred: ", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
