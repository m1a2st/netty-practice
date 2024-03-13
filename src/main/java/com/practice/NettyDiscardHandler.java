package com.practice;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyDiscardHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        try {
            log.info("收到消息，丟棄如下: ");
            while (in.isReadable()) {
                log.info(String.valueOf((char) in.readByte()));
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }

    }
}
