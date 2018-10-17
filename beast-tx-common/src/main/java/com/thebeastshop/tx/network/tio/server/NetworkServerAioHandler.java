/**
 * <p>Title: beast-tx</p >
 * <p>Description: 分布式事务框架，基于本地事务表模型，支持最终一致事务，TCC事务的事务框架平台</p >
 * @author Paul.Xiong
 * @email xiongleipaul@gmail.com
 * @Date 2018年10月12日
 */
package com.thebeastshop.tx.network.tio.server;

import java.nio.ByteBuffer;

import org.apache.commons.lang3.ArrayUtils;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.Tio;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;

import com.alibaba.fastjson.JSON;
import com.thebeastshop.tx.network.server.NetworkServerHandler;
import com.thebeastshop.tx.network.tio.NetworkPacket;
import com.thebeastshop.tx.network.tio.TioCoder;

public class NetworkServerAioHandler implements ServerAioHandler {

	public NetworkServerHandler handler;

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
			Object reply = handler.receive(body);
			if (reply != null) {
				NetworkPacket replyPacket = new NetworkPacket();
				replyPacket.setBody(JSON.toJSONString(reply).getBytes());
				Tio.send(channelContext, replyPacket);
			}
		}
	}
}