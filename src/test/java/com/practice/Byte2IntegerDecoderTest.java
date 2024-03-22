package com.practice;

import com.practice.decoder.Byte2IntegerDecode;
import com.practice.decoder.IntegerProcessHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

public class Byte2IntegerDecoderTest {

    @Test
    public void testDecode() {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new Byte2IntegerDecode());
                ch.pipeline().addLast(new IntegerProcessHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        for (int i = 0; i < 100; i++) {
            ByteBuf buffer = Unpooled.buffer();
            buffer.writeInt(i);
            channel.writeInbound(buffer);
        }
    }
}
