package client;

import handler.TimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import util.TimeDecoder;

public class TimeClient {
    public static void main(String[] args) {
//        String host = args[0];
//        int port = Integer.parseInt(args[1]);
        String host = "127.0.0.1";
        int port = 8080;
        /**
         * If you specify only one EventLoopGroup, it will be used both as a boss group and as a worker group.
         * The boss worker is not used for the client side though.
         */
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        /**
         * Bootstrap is similar to ServerBootstrap except that it's for non-server channels
         * such as a client-side or connectionless channel.
         */
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                /**
                 * Instead of NioServerSocketChannel, NioSocketChannel is being used to create a client-side Channel.
                 */
                .channel(NioSocketChannel.class)
                /**
                 * Note that we do not use childOption() here unlike we did with ServerBootstrap
                 * because the client-side SocketChannel does not have a parent.
                 */
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new TimeDecoder())
                                .addLast(new TimeClientHandler());
                    }
                });
        try {
            /**
             * We should call the connect() method instead of the bind() method.
             */
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }
}
