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
            // HttpObjectAggregator 將 HTTP 消息的多個部分合成一條完整的 HTTP 消息
            // HttpObjectAggregator 用於解析 Http 完整請求
            // 把多個消息轉換為一個單一的完全 FullHttpRequest 或是 FullHttpResponse，
            // 原因是 HTTP 解碼器會在解析每個 HTTP 消息中生成多個消息對象如 HttpRequest/HttpResponse,HttpContent,LastHttpContent
            pipeline.addLast(new HttpObjectAggregator(65535));
            // WebSocket 協議處理器，配置 WebSocket 監聽的 URI 路徑，協議包長度限制
            pipeline.addLast(new WebSocketServerProtocolHandler("/ws", "echo", true, 10 * 1024));
            // 增加網頁的處理邏輯
            pipeline.addLast(new WebPageHandler());
            // TextWebSocketFrameHandler 是自定義邏輯處理器
            pipeline.addLast(new TextWebSocketFrameHandler());
        }
    }

    public static void main(String ip) {
        // 創建連接監聽 reactor 線程組
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 創建連接處理 reactor 線程組
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 服務端啟動引導實例
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new EchoInitializer());
            // 綁定端口，同步等待成功
            Channel channel = serverBootstrap.bind(18999).sync().channel();
            log.info("Open your web browser and navigate to http://{}:{}/", ip, 18999);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("Error occurred: ", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
