package org.alpha;

import org.alpha.entity.RpcResponse;

/**
 * @author liyazhou1
 * @date 2019/12/8
 */
public class DefaultFuture {

    private RpcResponse response;

    private volatile boolean success = false;

    private final Object lockobj = new Object();


    /**
     * 消费者
     *
     * 消费者等待生产者的通知
     */
    public RpcResponse getResponse(int timeout) {
        synchronized (lockobj) {
            while(!success) {
                try {
                    lockobj.wait(timeout);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            return response;
        }
    }


    /**
     * 生产者
     *
     * 生产者有条件的执行生产并通知消费者
     */
    public void setResponse(RpcResponse response) {
        if (success) {
            return;
        }

        synchronized (lockobj) {
            this.response = response;
            this.success = true;
            lockobj.notify();
        }
    }
}
