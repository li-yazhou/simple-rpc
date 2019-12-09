package org.alpha.client;

import org.alpha.entity.RpcRequest;
import org.alpha.entity.RpcResponse;

import java.net.InetSocketAddress;

/**
 * @author liyazhou1
 * @date 2019/12/8
 */
public interface Client {

    RpcResponse send(RpcRequest request);

    void connect(InetSocketAddress inetSocketAddress);

    InetSocketAddress getInetSocketAddress();

    void close();
}
