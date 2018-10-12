/**
 * <p>Title: beast-tx</p >
 * <p>Description: 分布式事务框架，基于本地事务表模型，支持最终一致事务，TCC事务的事务框架平台</p >
 * @author Paul.Xiong
 * @email xiongleipaul@gmail.com
 * @Date 
 */
package com.thebeastshop.tx.record.storage.tio;

import org.tio.server.ServerGroupContext;
import org.tio.server.TioServer;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;

import com.thebeastshop.tx.record.storage.RecordStorageServer;

/**
 * 用t-io通信框架实现记录存储服务端
 */
public class RecordStorageTioServer implements RecordStorageServer {
	// handler, 包括编码、解码、消息处理
	public static ServerAioHandler aioHandler = new RecordServerAioHandler();

	// 事件监听器，可以为null，但建议自己实现该接口，可以参考showcase了解些接口
	public static ServerAioListener aioListener = null;

	// 一组连接共用的上下文对象
	public static ServerGroupContext serverGroupContext = new ServerGroupContext("record-tio-server", aioHandler, aioListener);

	// tioServer对象
	public static TioServer tioServer = new TioServer(serverGroupContext);

	// 有时候需要绑定ip，不需要则null
	public static String serverIp = null;

	// 监听的端口
	public static int serverPort = 6789;

	// 心跳超时时间
	public static final int timeout = 5000;

	@Override
	public void start() {
		try {
			serverGroupContext.setHeartbeatTimeout(timeout);
			tioServer.start(serverIp, serverPort);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}