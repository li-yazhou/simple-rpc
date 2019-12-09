package org.alpha;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.alpha.api.IHelloService;

/**
 * @author liyazhou1
 * @date 2019/12/8
 */
@Slf4j
@SpringBootApplication()
public class ClientApplication {

    public static void main(String[] args) {

        log.info("start client");

        ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class);

        log.info("get bean");

        IHelloService helloService = context.getBean(IHelloService.class);

        // log.info("helloService = {}", helloService);

        log.info(helloService.sayHi("Simple RPC"));
    }
}
