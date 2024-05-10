package com.practice.websocket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping 和 pong 幀已經被前面的 WebSocketServerProtocolHandler 處理器處理過了
        if (frame instanceof TextWebSocketFrame) {
            // 取得請求內容
            String request = ((TextWebSocketFrame) frame).text();
            log.info("Received: {}", request);
            // 回顯字符串： 當前時間 + 請求內容
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String echo = formatter.format(now) + ": " + request;
            TextWebSocketFrame echoFrame = new TextWebSocketFrame(echo);
            // 發送回顯字符串
            ctx.channel().writeAndFlush(echoFrame);
        } else {
            // 如果不是文本消息，抛出異常
            // 這個範例不支持二進制消息
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 添加連接
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 斷開連結
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    // 處理用戶資訊
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 判斷是否為握手成功事件，該事件表明通信協議以升級為 WebSocket
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            // 握手成功，移除 WebPageHandler，因此將不會收到任何消息
            ctx.pipeline().remove(WebPageHandler.class);
            log.info("WebSocket client {} joined", ctx.channel());
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
