package org.alpha.rpc.server.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.alpha.api.IHelloService;

/**
 * @author liyazhou1
 * @date 2019/12/8
 */
@Service
@Slf4j
public class HelloServiceImpl implements IHelloService {

    @Override
    public String sayHi(String name) {
        log.info(name);
        System.out.println("name = " + name);
        return "Hello, " + name;
    }

    @Override
    public String toString() {
        log.info("HelloServiceImpl toString method");
        return "HelloServiceImpl toString method";
    }
}
