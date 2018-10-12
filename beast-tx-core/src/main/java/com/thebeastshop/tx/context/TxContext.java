/**
 * <p>Title: beast-tx</p>
 * <p>Description: 分布式事务框架，基于本地事务表模型，支持最终一致事务，TCC事务的事务框架平台</p>
 * @author Bryan.Zhang
 * @email weenyc31@163.com
 * @Date 2018/9/30
 */
package com.thebeastshop.tx.context;

import com.thebeastshop.tx.context.content.InvokeContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 事务上下文
 */
public class TxContext {

    private final static Logger log = LoggerFactory.getLogger(TxContext.class);

    private String nodeId;

    private Long txId;

    private final ConcurrentLinkedDeque<InvokeContent> invokeQueue = new ConcurrentLinkedDeque<>();

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Long getTxId() {
        return txId;
    }

    public void setTxId(Long txId) {
        this.txId = txId;
    }

    public void addInvokeQueue(InvokeContent invokeContent){
        this.invokeQueue.add(invokeContent);
    }

}