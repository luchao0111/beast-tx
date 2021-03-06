/**
 * <p>Title: beast-tx</p >
 * <p>Description: 分布式事务框架，基于TCC事务的事务框架监控跟踪平台</p >
 * @author Paul.Xiong
 * @email xiongleipaul@gmail.com
 * @Date 2018年10月12日
 */
package com.thebeastshop.tx.socket.tio;

import org.tio.core.intf.Packet;

public class SocketPacket extends Packet {
	private static final long serialVersionUID = 1L;
	public static final int HEADER_LENGHT = 4;// 消息头的长度
	private byte[] body;

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}
}
