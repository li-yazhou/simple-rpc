package org.alpha.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

/**
 * @author liyazhou1
 * @date 2019/12/8
 */
@Slf4j
public class ProxyFactory {

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> interfaceClass) {
        log.info("interfaceClass = {}", interfaceClass);
        T t = (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new RpcInvoker<T>(interfaceClass)
        );
        // log.info("proxy object = {}", t.toString());
        return t;
    }
}
