/**
 * <p>Title: beast-tx</p >
 * <p>Description: 分布式事务框架，基于TCC事务的事务框架监控跟踪平台</p >
 * @author Paul.Xiong
 * @email xiongleipaul@gmail.com
 * @Date 2018年10月12日
 */
package com.thebeastshop.tx.network.tio.client;

import java.nio.ByteBuffer;

import org.apache.commons.lang3.ArrayUtils;
import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;

import com.thebeastshop.tx.network.client.NetworkClientHandler;
import com.thebeastshop.tx.network.tio.NetworkPacket;
import com.thebeastshop.tx.network.tio.TioCoder;

/**
 * 
 */
public class NetworkClientAioHandler implements ClientAioHandler {
	private static NetworkPacket heartbeatPacket = new NetworkPacket();

	public NetworkClientHandler handler;

	@Override
	public NetworkPacket decode(ByteBuffer buffer, int limit, int position, int readableLength, ChannelContext channelContext) throws AioDecodeException {
		return TioCoder.decode(buffer, limit, position, readableLength, channelContext);
	}

	@Override
	public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
		return TioCoder.encode(packet, groupContext, channelContext);
	}

	/**
	 * 处理消息
	 */
	@Override
	public void handler(Packet packet, ChannelContext channelContext) throws Exception {
		NetworkPacket storagePacket = (NetworkPacket) packet;
		byte[] body = storagePacket.getBody();
		if (ArrayUtils.isNotEmpty(body) && handler != null) {
			handler.handle(body);
		}
	}

	/**
	 * 此方法如果返回null，框架层面则不会发心跳；如果返回非null，框架层面会定时发本方法返回的消息包
	 */
	@Override
	public NetworkPacket heartbeatPacket() {
		return heartbeatPacket;
	}
}
