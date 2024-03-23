package com.practice;

import com.practice.encoder.Integer2ByteEncoder;
import com.practice.encoder.String2IntegerEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

public class String2integerEncoderTest {

    @Test
    public void testEncode() {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new Integer2ByteEncoder());
                ch.pipeline().addLast(new String2IntegerEncoder());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        for (int i = 0; i < 100; i++) {
            String msg = "I'm " + i;
            channel.write(msg);
        }
        channel.flush();
        // 取得通道的出站數據包
        ByteBuf byteBuf = channel.readOutbound();
        while (byteBuf != null) {
            System.out.println(byteBuf.readInt());
            byteBuf = channel.readOutbound();
        }
    }
}
