package org.alpha.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.alpha.client.Client;
import org.alpha.netty.handler.ClientHandler;
import org.alpha.coder.RpcDecoder;
import org.alpha.coder.RpcEncoder;
import org.alpha.entity.RpcRequest;
import org.alpha.entity.RpcResponse;
import org.alpha.serialization.JsonSerialization;

import java.net.InetSocketAddress;

/**
 * @author liyazhou1
 * @date 2019/12/8
 */
@Slf4j
public class NettyClient implements Client {

    private EventLoopGroup eventLoopGroup;

    private Channel channel;

    private ClientHandler clientHandler;

    private String host;

    private int port;


    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    @Override
    public RpcResponse send(final RpcRequest request) {
        log.info("send request = {}", request.toString());

        try {
            channel.writeAndFlush(request).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return clientHandler.getRpcResponse(request.getRequestId());
    }


    @Override
    public void connect(final InetSocketAddress inetSocketAddress) {

        this.clientHandler = new ClientHandler();

        this.eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                // .handler(new LoggingHandler(LogLevel.DEBUG))
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 4));
                        pipeline.addLast(new RpcEncoder(RpcRequest.class, new JsonSerialization()));
                        pipeline.addLast(new RpcDecoder(RpcResponse.class, new JsonSerialization()));
                        pipeline.addLast(clientHandler);
                    }
                });

        try {
            this.channel = bootstrap.connect(inetSocketAddress).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    public InetSocketAddress getInetSocketAddress() {

        return new InetSocketAddress(host, port);
    }

    @Override
    public void close() {
        eventLoopGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
    }
}
