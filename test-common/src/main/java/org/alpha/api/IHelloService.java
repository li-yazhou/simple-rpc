package org.alpha.api;

import org.alpha.annotation.RpcInterface;

/**
 * @author liyazhou1
 * @date 2019/12/8
 */
@RpcInterface
public interface IHelloService {

    String sayHi(String name);
}
