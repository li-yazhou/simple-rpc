package org.alpha;

import lombok.extern.slf4j.Slf4j;
import org.alpha.netty.NettyClient;
import org.alpha.entity.RpcRequest;
import org.alpha.entity.RpcResponse;

/**
 * @author liyazhou1
 * @date 2019/12/8
 */
@Slf4j
public class Transporters {

    public static RpcResponse send(RpcRequest request) {

        NettyClient nettyClient = new NettyClient("127.0.0.1", 8080);

        nettyClient.connect(nettyClient.getInetSocketAddress());

        RpcResponse response = nettyClient.send(request);

        log.info("Transporters response = {}", response);

        return response;
    }
}
