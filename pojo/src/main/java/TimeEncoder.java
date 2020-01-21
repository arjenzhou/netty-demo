import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @Author zhouyang
 * @Date 2020/1/21 10:19 下午
 * @Version 1.0
 * @Description
 */
public class TimeEncoder extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        UnixTime m = (UnixTime) msg;
        ByteBuf encoded = ctx.alloc().buffer(4);
        encoded.writeInt(((int) m.value()));
        /*
         * ChannelPromise
         * Special ChannelFuture which is writable.
         *
         * ctx.write()
         * Request to write a message via this ChannelHandlerContext
         * through the ChannelPipeline.
         * This method will not request to actual flush,
         * so be sure to call flush() once you want to request to flush
         * all pending data to the actual transport.
         *
         * we did not call ctx.flush().
         * There is a separate handler method void flush(ChannelHandlerContext ctx)
         * which is purposed to override the flush() operation.
         */
        ctx.write(encoded, promise);
    }
}

//public class TimeEncoder extends MessageToByteEncoder<UnixTime> {
//    @Override
//    protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) {
//        out.writeInt((int)msg.value());
//    }
//}