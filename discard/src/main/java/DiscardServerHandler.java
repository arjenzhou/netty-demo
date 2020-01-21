/**
 * @Author zhouyang
 * @Date 2020/1/21 5:41 下午
 * @Version 1.0
 * @Description
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ((ByteBuf) msg).release();

        /*
         * ByteBuf is a reference-counted object
         * which has to be released explicitly via the release() method.
         *
         * Please keep in mind that it is the handler's responsibility
         * to release any reference-counted object passed to the handler.
         * */
        try {
            //pass
        } catch (Exception e) {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

