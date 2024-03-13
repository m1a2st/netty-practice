package com.practice;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

public class InHandlerDemoTest {

    @Test
    public void testInHandlerLifeCycle() {
        InHandlerDemo inHandlerDemo = new InHandlerDemo();

        ChannelInitializer<EmbeddedChannel> init = new ChannelInitializer<>() {
            // 初始化處理器
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(inHandlerDemo);
            }
        };
        // 創建嵌入式通道
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(init);
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(1);
        // 模擬入棧，向嵌入式通道中寫入數據
        embeddedChannel.writeInbound(buffer);
        embeddedChannel.flush();
        // 關閉通道
        embeddedChannel.close();
    }
}
