package com.practice;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

public class InPipelineTest {

    @Test
    public void testPipelineInBound() {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new InPipeline.SimpleHandlerA());
                ch.pipeline().addLast(new InPipeline.SimpleHandlerB());
                ch.pipeline().addLast(new InPipeline.SimpleHandlerC());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(10);
        channel.writeInbound(buf);
        channel.writeInbound(buf);
        channel.writeInbound(buf);
    }
}
