package www.atunk.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import www.atunk.netty.handler.server.ServerInboundHandler1;

import java.net.InetSocketAddress;

/**
 * @description: Netty服务端
 * @author: 张军
 * @email: 23166321@qq.com
 * @date: 2023/6/5 09:52
 */
public class NettyServer {
	public static void main(String[] args) {
		NettyServer nettyServer = new NettyServer();
		nettyServer.startServer(9999);
	}

	private void startServer(int port) {
		EventLoopGroup boosGroup = new NioEventLoopGroup(1);
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();

		try {
			serverBootstrap.group(boosGroup, worker)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							ChannelPipeline pipeline = socketChannel.pipeline();
							pipeline.addLast(new ServerInboundHandler1());
						}
					});

			ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(port)).sync();

			future.channel().closeFuture().sync();


		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			boosGroup.shutdownGracefully();
			worker.shutdownGracefully();
		}

	}
}
