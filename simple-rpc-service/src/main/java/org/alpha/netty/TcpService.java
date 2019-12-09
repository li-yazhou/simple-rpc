package org.alpha.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @author liyazhou1
 * @date 2019/12/8
 */
@Data
@Component
@Slf4j
public class TcpService {

    private final ServerBootstrap serverBootstrap;

    private final InetSocketAddress tcpPort;

    public TcpService(ServerBootstrap serverBootstrap, InetSocketAddress inetSocketAddress) {
        this.serverBootstrap = serverBootstrap;
        this.tcpPort = inetSocketAddress;
    }

    private Channel serverChannel;


    public void start() throws InterruptedException {
        log.info("TcpService start");
        serverChannel = serverBootstrap.bind(tcpPort).sync().channel().closeFuture().channel();
    }

    /**
     * TODO
     *
     * javax.annotation.PreDestroy
     */
    @PreDestroy
    public void stop() {
        if (serverChannel != null) {
            serverChannel.close();
            serverChannel.parent().close();
        }
    }

}
