package com.practice;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ByteBufferTest {

    @Test
    public void testRef() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        log.info("buffer create: {}", buffer.refCnt());

        // retain()方法增加引用計數
        buffer.retain();
        log.info("buffer retain: {}", buffer.refCnt());

        // release()方法减少引用計數
        buffer.release();
        log.info("buffer release: {}", buffer.refCnt());

        // release()方法减少引用計數
        buffer.release();
        log.info("buffer release: {}", buffer.refCnt());

        // 錯誤 refCnt: 0
//        buffer.retain();
//        log.info("buffer retain: {}", buffer.refCnt());

    }
}
