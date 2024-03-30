package com.practice.decoder;

import com.practice.object.JsonMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonMessageDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String json = (String) msg;
        JsonMessage jsonMessage = JsonMessage.convert2Object(json);
        log.info("收到一個JsonMessage: {}", jsonMessage);
    }
}
