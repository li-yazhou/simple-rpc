package org.alpha.proxy;

import lombok.extern.slf4j.Slf4j;
import org.alpha.Transporters;
import org.alpha.entity.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author liyazhou1
 * @date 2019/12/8
 */
@Slf4j
public class RpcInvoker<T> implements InvocationHandler {

    private Class<T> clazz;

    public RpcInvoker(Class<T> clazz) {
        this.clazz = clazz;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        log.info("method = {}", method.getName());

        RpcRequest request = new RpcRequest();

        String requestId = UUID.randomUUID().toString();

        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();

        request.setRequestId(requestId);
        request.setClassName(className);
        request.setMethodName(methodName);
        request.setParameterTypes(parameterTypes);
        request.setParameters(args);

        log.info("invoke request = {}", request);

        return Transporters.send(request).getResult();
    }
}
