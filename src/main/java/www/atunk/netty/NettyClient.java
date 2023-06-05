package www.atunk.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import www.atunk.netty.handler.client.ClientInboundHandler1;

import java.nio.charset.Charset;

/**
 * @description: Netty客户端
 * @author: 张军
 * @email: 23166321@qq.com
 * @date: 2023/6/5 10:13
 */
public class NettyClient {

	public static void main(String[] args) {
		NettyClient nettyClient = new NettyClient();
		NettyClient.startClient("127.0.0.1", 9999);
	}

	private static void startClient(String host, int port) {
		EventLoopGroup eventExecutors = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();

		try {
			bootstrap.group(eventExecutors)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							ChannelPipeline pipeline = socketChannel.pipeline();
							pipeline.addLast(new ClientInboundHandler1());
						}
					});

			ChannelFuture future = bootstrap.connect(host, port).sync();

			Channel channel = future.channel();
			ByteBuf buffer = channel.alloc().buffer();
			byte[] bytes = "hello NettyServer, I am NettyClient".getBytes(Charset.defaultCharset());
			buffer.writeBytes(bytes);
			channel.writeAndFlush(buffer);

			future.channel().closeFuture().sync();


		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			eventExecutors.shutdownGracefully();
		}

	}

}
