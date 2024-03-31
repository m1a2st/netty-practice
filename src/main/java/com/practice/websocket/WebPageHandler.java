package com.practice.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

import static io.netty.handler.codec.http.HttpMethod.GET;

@Slf4j
public class WebPageHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        if (!request.decoderResult().isSuccess()) {
//            HttpProtocolHelper.sendError(ctx, BAD_REQUEST);
            return;
        }

        if (!GET.equals(request.method())) {
//            HttpProtocolHelper.sendError(ctx, FORBIDDEN);
            return;
        }

//        HttpProtocolHelper.cacheHttpProtocol(ctx, request);
//
//        String webContent = IOUtil.loadResourceFile("index.html");

//        HttpProtocolHelper.sendWebPage(ctx, webContent);
    }


}
