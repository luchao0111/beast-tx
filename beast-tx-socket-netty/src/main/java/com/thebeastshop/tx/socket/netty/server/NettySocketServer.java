/**
 * <p>Title: beast-tx</p >
 * <p>Description: 分布式事务框架，基于TCC事务的事务框架监控跟踪平台</p >
 * @author Paul.Xiong
 * @email xiongleipaul@gmail.com
 * @Date 2018年10月12日
 */
package com.thebeastshop.tx.socket.netty.server;

import com.thebeastshop.tx.socket.config.ServerConfig;
import com.thebeastshop.tx.socket.server.SocketServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 用t-io通信框架实现服务端
 */
public class NettySocketServer implements SocketServer {

	private int port = 6789;

	private ServerBootstrap b;

	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	@Override
	public SocketServer initServer(final ServerConfig config) {
		final NettySocketServerHandler nettyServerHandler = new NettySocketServerHandler();
		nettyServerHandler.handler = config.getHandler();
		port = config.getPort();
		bossGroup = new NioEventLoopGroup(1);
		workerGroup = new NioEventLoopGroup();
		b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast(new ByteArrayDecoder());
		        p.addLast(new ByteArrayEncoder());
				p.addLast(nettyServerHandler);
			}
		});
		return this;
	}

	@Override
	public void start() {
		try {
			b.bind(port).sync();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}