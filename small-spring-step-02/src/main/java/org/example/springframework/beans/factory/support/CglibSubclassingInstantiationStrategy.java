package org.example.springframework.beans.factory.support;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.example.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, Constructor ctor, Object[] args) {
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(beanDefinition.getBeanDefinition());

        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return methodProxy.invokeSuper(o, objects);
            }
        });

        if (ctor != null) {
            // 生成代理对象
            return enhancer.create(ctor.getParameterTypes(), args);
        }
        return enhancer.create();
    }
}
