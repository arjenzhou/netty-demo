package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;


public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    //当接收到数据时被调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ((ByteBuf) msg).release();

        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) {
                System.out.print(((char) in.readByte()));
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg);
//            equals to in.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
