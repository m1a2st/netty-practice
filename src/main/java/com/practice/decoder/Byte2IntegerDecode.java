package com.practice.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class Byte2IntegerDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf byteBuf, List<Object> list) {
        while (byteBuf.readableBytes() >= 4) {
            int i = byteBuf.readInt();
            System.out.println("解碼出一個整數: " + i);
            list.add(i);
        }
    }
}
