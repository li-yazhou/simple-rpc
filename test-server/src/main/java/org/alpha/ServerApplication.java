package org.alpha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.alpha.netty.TcpService;

/**
 * @author liyazhou1
 * @date 2019/12/5
 */
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) throws InterruptedException {

        ConfigurableApplicationContext context = SpringApplication.run(ServerApplication.class);

        TcpService tcpService = context.getBean(TcpService.class);

        tcpService.start();
    }
}
