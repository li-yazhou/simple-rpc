package org.alpha.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.alpha.entity.RpcRequest;
import org.alpha.entity.RpcResponse;


/**
 * @author liyazhou1
 * @date 2019/12/6
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<RpcRequest> implements ApplicationContextAware {


    private ApplicationContext applicationContext;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) {
        log.info("read request = {}", msg.toString());

        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(msg.getRequestId());

        try {
            Object handler = handler(msg);
            rpcResponse.setResult(handler);
        } catch (Throwable throwable) {
            rpcResponse.setThrowable(throwable);
            throwable.printStackTrace();
        }

        ctx.writeAndFlush(rpcResponse);

    }

    private Object handler(RpcRequest request) throws Throwable {

        // 在application中获取service对象
        Class<?> clazz = Class.forName(request.getClassName());
        Object serviceBean = applicationContext.getBean(clazz);
        Class<?> serverClass = serviceBean.getClass();

        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        FastClass fastClass = FastClass.create(serverClass);
        FastMethod fastMethod = fastClass.getMethod(methodName, parameterTypes);

        return fastMethod.invoke(serviceBean, parameters);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
