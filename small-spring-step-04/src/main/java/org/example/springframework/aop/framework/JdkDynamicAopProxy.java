package org.example.springframework.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.example.springframework.aop.AdviceSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private AdviceSupport adviceSupport;

    public JdkDynamicAopProxy(AdviceSupport adviceSupport) {
        this.adviceSupport = adviceSupport;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), adviceSupport.getTargetSource().getTargetClass(), this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {

        if (adviceSupport.getMethodMatcher().matches(method, adviceSupport.getTargetSource().getTarget().getClass())) {
            MethodInterceptor methodInterceptor = adviceSupport.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(method, args, adviceSupport.getTargetSource().getTarget()));
        }

        return method.invoke(adviceSupport.getTargetSource().getTarget(), args);
    }
}
