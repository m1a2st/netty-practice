package com.practice.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@ChannelHandler.Sharable
public class EchoNettyClientHandler extends ChannelInboundHandlerAdapter {

    public static final EchoNettyClientHandler INSTANCE = new EchoNettyClientHandler();

    private EchoNettyClientHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        int len = byteBuf.readableBytes();
        byte[] bytes = new byte[len];
        byteBuf.getBytes(0, bytes);
        log.info("client received: {}", new String(bytes, UTF_8));
        // 釋放 byteBuf 的兩種方法
        // 手動釋放
        byteBuf.release();
        // 調用父類的入站方法將 msg 向後傳遞
        // super.channelRead(ctx, msg);
    }
}
