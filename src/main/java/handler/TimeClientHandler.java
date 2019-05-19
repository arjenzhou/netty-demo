package handler;

import POJO.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    private ByteBuf buf;

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        buf.release();
        buf = null;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        buf = ctx.alloc().buffer(4);
    }

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf m = (ByteBuf) msg;
//        long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
//        System.out.println(new Date(currentTimeMillis));
//        ctx.close();
//        buf.writeBytes(m);
//        m.release();
//
//        //防止粘包的第一种方法
////        if (buf.readableBytes() >= 4) {
////            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
////            System.out.println(new Date(currentTimeMillis));
////            ctx.close();
////        }
//    }

    //使用 POJO 代替 ByteBuf
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UnixTime m = (UnixTime) msg;
        System.out.println(m);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
