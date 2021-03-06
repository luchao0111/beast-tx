/**
 * <p>Title: beast-tx</p >
 * <p>Description: 分布式事务框架，基于TCC事务的事务框架监控跟踪平台</p >
 * @author Paul.Xiong
 * @email xiongleipaul@gmail.com
 * @Date 2018年10月12日
 */
package com.thebeastshop.tx.socket.tio.client;

import com.alibaba.fastjson.JSON;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Node;
import org.tio.core.Tio;

import com.thebeastshop.tx.socket.client.SocketClient;
import com.thebeastshop.tx.socket.config.ClientConfig;
import com.thebeastshop.tx.socket.tio.SocketPacket;

/**
 * 用t-io通信框架实现客户端
 */
public class TioSocketClient implements SocketClient {

	private ClientChannelContext clientChannelContext;

	@Override
	public SocketClient initClient(ClientConfig config) {
		TioSocketClientAioHandler tioClientHandler = new TioSocketClientAioHandler();
		tioClientHandler.handler = config.getHandler();
		ClientAioListener aioListener = null;
		ReconnConf reconnConf = new ReconnConf(5000L);
		ClientGroupContext clientGroupContext = new ClientGroupContext(tioClientHandler, aioListener, reconnConf);
		clientGroupContext.setHeartbeatTimeout(config.getTimeout());
		TioClient tioClient;
		try {
			tioClient = new TioClient(clientGroupContext);
			Node serverNode = new Node(config.getIp(), config.getPort());
			clientChannelContext = tioClient.connect(serverNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public <T> void send(T t) {
		SocketPacket packet = new SocketPacket();
		packet.setBody(JSON.toJSONString(t).getBytes());
		Tio.send(clientChannelContext, packet);
	}
}
