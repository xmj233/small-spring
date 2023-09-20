package org.example.springframework.aop.framework;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.example.springframework.aop.AdviceSupport;

import java.lang.reflect.Method;

public class Cglib2AopProxy implements AopProxy {

    private AdviceSupport adviceSupport;

    public Cglib2AopProxy(AdviceSupport adviceSupport) {
        this.adviceSupport = adviceSupport;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(adviceSupport.getTargetSource().getTargetClass());
        enhancer.setSuperclass(adviceSupport.getTargetSource().getTarget().getClass());
        enhancer.setCallback(new DynamicAdvisedInterceptor());

        return enhancer.create();
    }

    private class DynamicAdvisedInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            CglibMethodInvocation methodInvocation = new CglibMethodInvocation(method, objects, adviceSupport.getTargetSource().getTarget(), methodProxy);

            if (adviceSupport.getMethodMatcher().matches(method, adviceSupport.getTargetSource().getTarget().getClass())) {
                return adviceSupport.getMethodInterceptor().invoke(methodInvocation);
            }
            
            return methodInvocation.proceed();
        }
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

        private MethodProxy methodProxy;

        public CglibMethodInvocation(Method method, Object[] args, Object target, MethodProxy methodProxy) {
            super(method, args, target);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return this.methodProxy.invoke(this.target, this.args);
        }

    }
}
