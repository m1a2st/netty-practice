package im.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


/**
 * Head-Content 數據包模式
 * <p>
 * 魔數： 2 byte
 * 版本號：2 byte
 * 長度： 4 byte
 * 內容（content）：N byte
 */
public class SimpleProtobufDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // 記錄當前的 readIndex 位置
        in.markReaderIndex();

        // 判斷包頭是否足夠
        if (in.readableBytes() < 2) {
            return;
        }

        // 讀取傳送過來的消息的長度
        int length = in.readUnsignedShort();

        // 長度如果小於 0，為非法數據，關閉連接
        if (length < 0) {
            ctx.close();
        }

        // 如果讀到的消息體長度小於傳送過來的消息長度
        if (length > in.readableBytes()) {
            // 重置讀取位置
            in.resetReaderIndex();
            return;
        }

        byte[] array;
        int offset = 0;
        if (in.hasArray()) {
            // 堆緩衝
            ByteBuf slice = in.slice();
            array = slice.array();
        } else {
            // 直接緩衝
            array = new byte[length];
            in.readBytes(array, 0, length);
        }

        // 字節轉成對象
    }
}
