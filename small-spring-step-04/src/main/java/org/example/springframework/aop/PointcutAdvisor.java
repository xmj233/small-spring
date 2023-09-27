package org.example.springframework.aop;

public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();

}
