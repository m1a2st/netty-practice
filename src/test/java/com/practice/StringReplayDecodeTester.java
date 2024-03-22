package com.practice;

import com.practice.decoder.StringProcessHandler;
import com.practice.decoder.StringReplayDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class StringReplayDecodeTester {

    static final String content = "測試字符串，需要解碼。";

    @Test
    public void testStringReplayDecoder() {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new StringReplayDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        // 代發送的字符串 content 的字節數
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
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
            // 發送內容
            channel.writeInbound(buffer);
        }
    }
}
