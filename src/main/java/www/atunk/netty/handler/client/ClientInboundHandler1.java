package www.atunk.netty.handler.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @description: 客户端处理器1，用于接收服务端发送的数据，并打印到控制台上，然后将数据返回给服务端，服务端接收到数据后打印到控制台上。
 * @author: 张军
 * @email: 23166321@qq.com
 * @date: 2023/6/5 10:38
 */
public class ClientInboundHandler1 extends ChannelInboundHandlerAdapter {

	/**
	 * 当一个Channel处于活动状态并准备处理流量时，会调用此方法。
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ClientInboundHandler1#channelActive");
		//super.channelActive(ctx);
	}

	/**
	 * 当一个Channel处于非活动状态时，会调用此方法。
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ClientInboundHandler1#channelInactive");
		//super.channelInactive(ctx);
	}

	/**
	 * 当从Channel读取数据时被调用。
	 * @param ctx
	 * @param msg
	 * @throws Exception
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("ClientInboundHandler1#channelRead");
		ByteBuf buf = (ByteBuf) msg;
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);

		String message = new String(bytes, Charset.defaultCharset());
		System.out.println(message);
		//super.channelRead(ctx, msg);
	}

	/**
	 * 当Channel上的一个读操作完成时被调用。
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ClientInboundHandler1#channelReadComplete");
		//super.channelReadComplete(ctx);
	}

	/**
	 * 在读取操作期间，有异常抛出时会调用此方法。
	 * @param ctx
	 * @param cause
	 * @throws Exception
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("ClientInboundHandler1#exceptionCaught");
		//super.exceptionCaught(ctx, cause);
	}

}
