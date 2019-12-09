package org.alpha.config;

import lombok.extern.slf4j.Slf4j;
import org.alpha.proxy.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import org.reflections.Reflections;
import org.springframework.context.annotation.Configuration;
import org.alpha.annotation.RpcInterface;

import java.util.Set;


/**
 * @author liyazhou1
 * @date 2019/12/8
 */
@Configuration
@Slf4j
public class RpcConfig implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;


    @Override
    public void afterPropertiesSet() throws Exception{
        Reflections reflections = new Reflections("org.alpha");
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(RpcInterface.class);
        for (Class<?> clazz : typesAnnotatedWith) {
            beanFactory.registerSingleton(clazz.getSimpleName(), ProxyFactory.create(clazz));
        }
        log.info("afterPropertiesSet is {}", typesAnnotatedWith);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
