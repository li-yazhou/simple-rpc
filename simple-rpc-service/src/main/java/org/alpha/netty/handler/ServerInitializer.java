package org.alpha.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.alpha.coder.RpcDecoder;
import org.alpha.coder.RpcEncoder;
import org.alpha.entity.RpcRequest;
import org.alpha.entity.RpcResponse;
import org.alpha.serialization.JsonSerialization;

/**
 * @author liyazhou1
 * @date 2019/12/6
 */
@Component
public class ServerInitializer extends ChannelInitializer<SocketChannel> {


    @Autowired
    private ServerHandler serverHandler;


    @Override
    protected void initChannel(SocketChannel socketChannel) {

        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 4))
                .addLast(new RpcEncoder(RpcResponse.class, new JsonSerialization()))
                .addLast(new RpcDecoder(RpcRequest.class, new JsonSerialization()))
                .addLast(serverHandler);
    }
}
