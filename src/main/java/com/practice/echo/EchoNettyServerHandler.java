package com.practice.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 此 Handler 可以被多個 Channel 安全地共享，
 * 多個流水線共用同一個 Handler 實例
 */
@ChannelHandler.Sharable
@Slf4j
public class EchoNettyServerHandler extends ChannelInboundHandlerAdapter {

    public static final EchoNettyServerHandler INSTANCE = new EchoNettyServerHandler();

    private EchoNettyServerHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        log.info("msg type:" + (in.hasArray() ? "堆內存" : "直接內存"));
        int len = in.readableBytes();
        byte[] arr = new byte[len];
        in.getBytes(0, arr);
        log.info("server received: {}", new String(arr, UTF_8));
        ChannelFuture channelFuture = ctx.writeAndFlush(msg);
        channelFuture.addListener(future -> log.info("消息發送成功"));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("發生異常", cause);
        ctx.close();
    }
}
