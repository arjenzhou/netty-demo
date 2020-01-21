import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * @Author zhouyang
 * @Date 2020/1/21 7:27 下午
 * @Version 1.0
 * @Description
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            System.out.println(((ByteBuf) msg).toString(io.netty.util.CharsetUtil.US_ASCII));

            ByteBuf byteBuf = (ByteBuf) msg;
            byte[] req = new byte[byteBuf.readableBytes()];
            /*
             * readBytes(byte[] dst)
             * Transfers this buffer's data to the specified destination
             * starting at the current readerIndex and increases the readerIndex
             * by the number of the transferred bytes (= dst.length).
             */
            byteBuf.readBytes(req);
            String body = new String(req, "UTF-8");
            String response = "Hello, " + body;
            /*
             * copiedBuffer() returns ByteBuf
             * Creates a new big-endian buffer whose content is a copy of the specified array.
             * The new buffer's readerIndex and writerIndex are 0 and array.length respectively.
             */
            ctx.write(Unpooled.copiedBuffer(response.getBytes()));

            /*
             * ctx.write(Object) does not make the message written out to the wire.
             * It is buffered internally and then flushed out to the wire by ctx.flush().
             * Alternatively, you could call ctx.writeAndFlush(msg) for brevity.
             */
//            ctx.flush();
//            ctx.writeAndFlush(msg);
        } catch (Exception e) {
            ReferenceCountUtil.release(msg);
        }
    }

    /*
     * Invoked when the last message read by the current read operation
     * has been consumed by channelRead(ChannelHandlerContext, Object).
     * If ChannelOption.AUTO_READ is off,
     * no further attempt to read an inbound data from the current Channel
     * will be made until ChannelHandlerContext.read() is called.
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
