package org.example.springframework.aop.framework;

import jdk.jfr.internal.OldObjectSample;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

public class ReflectiveMethodInvocation implements MethodInvocation {

    protected Method method;

    protected Object[] args;

    protected Object target;

    public ReflectiveMethodInvocation(Method method, Object[] args, Object target) {
        this.method = method;
        this.args = args;
        this.target = target;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return args;
    }

    @Override
    public Object proceed() throws Throwable {
        return method.invoke(target, args);
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}
