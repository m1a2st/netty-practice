package com.practice.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class StringReplayDecoder extends ReplayingDecoder<StringReplayDecoder.PHASE> {

    enum PHASE {
        PHASE_1, // 第一個階段，解碼出字符串的長度
        PHASE_2 // 第二個階段，按照字符串的長度解碼出字符串
    }

    private int length;
    private byte[] inBytes;

    public StringReplayDecoder() {
        super(PHASE.PHASE_1);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()) {
            case PHASE_1:
                // 從裝飾器 ByteBuf 中讀取字符串的長度
                length = in.readInt();
                // 進入下一個階段
                // 並設置 "讀指針檢查點" 為當前 readerIndex 的位置
                checkpoint(PHASE.PHASE_2);
                break;
            case PHASE_2:
                // 根據字符串的長度解碼出字符串
                inBytes = new byte[length];
                in.readBytes(inBytes);
                out.add(new String(inBytes));
                // 進入下一個階段
                // 並設置 "讀指針檢查點" 為當前 readerIndex 的位置
                checkpoint(PHASE.PHASE_1);
                break;
            default:
                break;
        }
    }

}
