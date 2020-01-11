package netty.demo;

import aio.server.TimeServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * <code>TimeSever</code> description
 *
 * @author sunqiyuan
 * @date 2020-01-11
 */
public class TimeSever {
    public void bind(int port) throws Exception {
        // eventLoopGroup是个线程组，包含一组NIO线程，专门用于处理网络事件，
        // 实际上就是Reactor线程组，bossGroup用于接受连接，worKGroup用于处理读写
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            // netty用于启动NIO服务端的辅助启动类，降低开发复杂类
            ServerBootstrap b = new ServerBootstrap();
            // 接受线程组，设置创建的channel为NioServerSocketChannel（对应ServerSocketChannel）
            // 配置 backlog为1024，最大接受线程，最后绑定I/O事件的处理类ChildChannelHandler,它的作用类似于
            // Reactor模式中的handler类，主要用于处理网络I/O事件，比如记录日志，对消息编解码
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());
            // 绑定端口，同步等待成功。同步阻塞方法等待绑定完成
            // 返回的ChannelFuture类似于JDK的 java.util.concurrent.Future
            ChannelFuture f = b.bind(port).sync();
            // 进行方法阻塞，等待服务断链路关闭之后main才退出
            f.channel().closeFuture().sync();
        } finally {
            // 优雅的退出线程组，会释放相关资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        protected void initChannel(SocketChannel arg0) throws Exception {
            arg0.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        new TimeSever().bind(8080);
    }
}
