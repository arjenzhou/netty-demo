/**
 * @Author zhouyang
 * @Date 2020/1/21 6:40 下午
 * @Version 1.0
 * @Description
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {
    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        /*
         * accepts an incoming connection
         */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        /*
         * handles the traffic of the accepted connection
         * once the boss accepts the connection and
         * registers the accepted connection to the worker
         */
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            /*
             * a helper class that sets up a server
             */
            ServerBootstrap b = new ServerBootstrap();
            /*
             * Set the EventLoopGroup for the parent (acceptor) and the child (client).
             */
            b.group(bossGroup, workerGroup)
                    /*
                     *  channel(Class channelClass)
                     *  The Class(NioServerSocketChannel) which is used to
                     *  create Channel instances from.
                     *
                     *  instantiate a new Channel to accept incoming connections.
                     */
                    .channel(NioServerSocketChannel.class)
                    /*
                     *  childHandler()
                     *  Set the ChannelHandler which is used to serve the request for the Channel's.
                     *
                     *  ChannelInitializer
                     *  A special ChannelInboundHandler(extends ChannelHandler) which offers an easy way to
                     *  initialize a Channel once it was registered to its EventLoop.
                     */
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        /*
                         * This method will be called once the Channel was registered.
                         * After the method returns this instance will be removed
                         * from the ChannelPipeline of the Channel.
                         */
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    /*
                     * option() is for the NioServerSocketChannel that accepts incoming connections.
                     * childOption() is for the Channels accepted by the parent ServerChannel
                     *
                     * SO_BACKLOG will keep queue that allows incoming request.
                     */
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            /*
             * The result of an asynchronous Channel I/O operation.
             *
             * bind()
             * Create a new Channel and bind it.
             *
             * ChannelFuture.sync()
             * Waits for this future until it is done,
             * and rethrows the cause of the failure if this future failed.
             */
            ChannelFuture f = b.bind(port).sync();
            /*
             * closeFuture()
             * Returns the ChannelFuture which will be notified
             * when this channel is closed.
             * This method always returns the same future instance.
             */
            f.channel().closeFuture().sync();
        } finally {
            /*
             * Signals this executor that the caller wants the executor to be shut down.
             * Once this method is called, isShuttingDown() starts to return true,
             * and the executor prepares to shut itself down.
             * Unlike shutdown(), graceful shutdown ensures that no tasks are submitted
             * for 'the quiet period' (usually a couple seconds) before it shuts itself down.
             * If a task is submitted during the quiet period,
             * it is guaranteed to be accepted and the quiet period will start over.
             */
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new DiscardServer(port).run();
    }
}

