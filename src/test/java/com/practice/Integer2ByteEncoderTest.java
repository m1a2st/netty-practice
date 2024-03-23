package com.practice;

import com.practice.encoder.Integer2ByteEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

public class Integer2ByteEncoderTest {

    @Test
    public void testEncode() {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new Integer2ByteEncoder());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        // 向通道寫入整數
        for (int i = 0; i < 100; i++) {
            channel.write(i);
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
