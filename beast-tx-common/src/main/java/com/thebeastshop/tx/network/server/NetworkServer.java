/**
 * <p>Title: beast-tx</p >
 * <p>Description: 分布式事务框架，基于TCC事务的事务框架监控跟踪平台</p >
 * @author Paul.Xiong
 * @email xiongleipaul@gmail.com
 * @Date 2018年10月12日
 */
package com.thebeastshop.tx.network.server;

import com.thebeastshop.tx.network.config.ServerConfig;

/**
 * 服务端
 */
public interface NetworkServer {

	NetworkServer initServer(ServerConfig config);

	/**
	 * 启动程序入口
	 * 
	 */
	void start();
}
