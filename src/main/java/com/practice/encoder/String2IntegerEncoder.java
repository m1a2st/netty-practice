package com.practice.encoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class String2IntegerEncoder extends MessageToMessageEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, List<Object> list) {
        char[] charArray = s.toCharArray();
        for (char c : charArray) {
           if (c >= 48 && c <= 57) {
                list.add(Integer.parseInt(String.valueOf(c)));
           }
        }
    }
}
