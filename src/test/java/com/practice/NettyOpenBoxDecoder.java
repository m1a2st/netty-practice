package com.practice;

import com.practice.decoder.StringProcessHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class NettyOpenBoxDecoder {

    static String SEPARATOR = "\r\n";
    static String CONTENT = "測試字符串，需要解碼。";

    @Test
    public void testLineBasedFrameDecoder() {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        byte[] bytes = CONTENT.getBytes(StandardCharsets.UTF_8);
        // 循環發送 100 次，每一輪可以理解為發送一個 Head-Content 報文
        for (int i = 0; i < 100; i++) {
            // 隨機 1 到 3 次 "測試字符串，需要解碼。"
            Random random = new Random();
            int randomInt = random.nextInt(4);
            ByteBuf buffer = Unpooled.buffer();
            // 發送長度
            buffer.writeInt(bytes.length * randomInt);
            // 重複拷貝 content 的字節數至發送緩衝區
            for (int j = 0; j < randomInt; j++) {
                buffer.writeBytes(bytes);
            }
            buffer.writeBytes(SEPARATOR.getBytes(StandardCharsets.UTF_8));
            // 發送內容
            channel.writeInbound(buffer);
        }
    }

    @Test
    public void testLengthFieldBasedFrameDecoder() {
        LengthFieldBasedFrameDecoder decoder = new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4);
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(decoder);
                ch.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        try {
            for (int i = 0; i < 100; i++) {
                ByteBuf buffer = Unpooled.buffer();
                String msg = i + "次發送 ->" + CONTENT;
                byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
                buffer.writeInt(bytes.length);
                buffer.writeBytes(bytes);
                channel.writeInbound(buffer);
            }

            Thread.sleep(100000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
