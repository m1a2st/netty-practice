package com.practice.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class StringIntegerHeaderDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 如果可讀字節小於 4，則返回
        if (in.readableBytes() < 4) {
            return;
        }
        // 消息頭已經完整
        // 在真正開始從緩衝區讀取數據之前，使用 markReaderIndex() 方法設置 mark
        in.markReaderIndex();
        int length = in.readInt();
        // 從緩衝區讀取消息頭的大小，會導致 readerIndex 讀指針變化
        // 如果剩餘長度不夠消息體長度，則重置 readerIndex，下一次從相同的位置開始讀取
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        out.add(new String(bytes));
    }
}
