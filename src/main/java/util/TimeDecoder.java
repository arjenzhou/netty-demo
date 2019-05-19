package util;

import POJO.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * decode
 */
public class TimeDecoder extends ReplayingDecoder {
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return;
        }

//        list.add(internalBuffer().readBytes(4));
        list.add(new UnixTime(internalBuffer().readUnsignedInt()));

    }
}
