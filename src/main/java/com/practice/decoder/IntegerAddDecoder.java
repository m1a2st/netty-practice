package com.practice.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class IntegerAddDecoder extends ReplayingDecoder<IntegerAddDecoder.PHASE> {

    private int first;
    private int second;

    public enum PHASE {
        PHASE_1, // 第一個階段，僅僅提取出第一個整數，完成後進入第二階段
        PHASE_2 // 第二個階段，提取第二個整數，並加上第一個整數，並且輸出結果
    }

    public IntegerAddDecoder() {
        // 初始化父類的 state 屬性，用來記錄當前的階段
        super(PHASE.PHASE_1);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()) {
            case PHASE_1:
                first = in.readInt();
                checkpoint(PHASE.PHASE_2);
                break;
            case PHASE_2:
                second = in.readInt();
                out.add(first + second);
                checkpoint(PHASE.PHASE_1);
                break;
            default:
                break;
        }
    }
}
