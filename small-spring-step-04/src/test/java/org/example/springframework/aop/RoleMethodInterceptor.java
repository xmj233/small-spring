package org.example.springframework.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class RoleMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            return methodInvocation.proceed();
        } finally {
            System.out.println("开始监控: aop");
            System.out.println("方法名: " + methodInvocation.getMethod().getName());
            System.out.println("方法耗时: " + (System.currentTimeMillis() - start) + "ms");
            System.out.println("结束监控： aop");
        }
    }
}
