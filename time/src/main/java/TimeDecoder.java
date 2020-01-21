import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Author zhouyang
 * @Date 2020/1/21 9:39 下午
 * @Version 1.0
 * @Description
 */
public class TimeDecoder extends ByteToMessageDecoder {
    /*
     * with an internally maintained cumulative buffer whenever new data is received.
     */
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            /*
             * decode() can decide to add nothing to list
             * where there is not enough data in the cumulative buffer.
             * ByteToMessageDecoder will call decode() again when there is more data received.
             */
            return;
        }
        /*
         * If decode() adds an object to list, it means the decoder decoded a message successfully.
         * ByteToMessageDecoder will discard the read part of the cumulative buffer.
         * Please remember that you don't need to decode multiple messages.
         * ByteToMessageDecoder will keep calling the decode() method until it adds nothing to list.
         *
         * readBytes(int length)
         * Transfers this buffer's data to a newly created buffer
         * starting at the current readerIndex and increases the readerIndex
         * by the number of the transferred bytes (= length).
         * The returned buffer's readerIndex and writerIndex are 0 and length respectively.
         */
        list.add(byteBuf.readBytes(4));
    }
}

//public class TimeDecoder extends ReplayingDecoder<Void> {
//    /*
//     * Called once data should be decoded from the given ByteBuf.
//     * This method will call ByteToMessageDecoder.decode(ChannelHandlerContext, ByteBuf, List)
//     * as long as decoding should take place.
//     */
//    @Override
//    protected void decode(
//            ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
//        out.add(in.readBytes(4));
//    }
//}
