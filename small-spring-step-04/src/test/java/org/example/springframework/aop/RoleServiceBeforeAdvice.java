package org.example.springframework.aop;

import java.lang.reflect.Method;

public class RoleServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) {
        System.out.println("拦截方法: " + method.getName());
    }
}
