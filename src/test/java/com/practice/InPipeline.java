package com.practice;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class InPipeline {

    /**
     * 第一個入站處理器
     */
    static class SimpleHandlerA extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("SimpleHandlerA channelRead");
            super.channelRead(ctx, msg);
        }
    }

    /**
     * 第二個入站處理器
     */
    static class SimpleHandlerB extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("SimpleHandlerB channelRead");
            super.channelRead(ctx, msg);
            ctx.pipeline().remove(this);
        }
    }

    /**
     * 第三個入站處理器
     */
    static class SimpleHandlerC extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("SimpleHandlerC channelRead");
            super.channelRead(ctx, msg);
            ctx.pipeline().remove(this);
        }
    }
}
