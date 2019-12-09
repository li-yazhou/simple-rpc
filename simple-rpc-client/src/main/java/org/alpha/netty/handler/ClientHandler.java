package org.alpha.netty.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;
import org.alpha.DefaultFuture;
import org.alpha.entity.RpcRequest;
import org.alpha.entity.RpcResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liyazhou1
 * @date 2019/12/8
 */
@Slf4j
public class ClientHandler extends ChannelDuplexHandler {

    private final Map<String, DefaultFuture> futureMap = new ConcurrentHashMap<>();


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof RpcRequest) {
            RpcRequest request = (RpcRequest) msg;
            futureMap.putIfAbsent(request.getRequestId(), new DefaultFuture());
            log.info("write request = {}", request);
        }
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("client read msg = {}", msg);
        if (msg instanceof RpcResponse) {
            RpcResponse response = (RpcResponse) msg;
            DefaultFuture defaultFuture = futureMap.get(response.getRequestId());

            defaultFuture.setResponse(response);
            log.info("read response = {}", response);
        }
        super.channelRead(ctx, msg);
    }


    public RpcResponse getRpcResponse(String requestId) {

        try {
            DefaultFuture defaultFuture = futureMap.get(requestId);
            RpcResponse response = defaultFuture.getResponse(10);
            log.info("get response = {}", response);
            return response;
        } finally {
            futureMap.remove(requestId);
        }
    }
}
