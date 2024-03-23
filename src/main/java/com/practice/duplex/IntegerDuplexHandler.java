package com.practice.duplex;

import com.practice.decoder.Byte2IntegerDecode;
import com.practice.encoder.Integer2ByteEncoder;
import io.netty.channel.CombinedChannelDuplexHandler;

public class IntegerDuplexHandler extends CombinedChannelDuplexHandler<Byte2IntegerDecode, Integer2ByteEncoder> {

    public IntegerDuplexHandler() {
        super(new Byte2IntegerDecode(), new Integer2ByteEncoder());
    }
}
