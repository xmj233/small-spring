package org.example.springframework.aop.aspectj;

import org.aopalliance.aop.Advice;
import org.example.springframework.aop.Pointcut;
import org.example.springframework.aop.PointcutAdvisor;

public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    private Advice advice;

    private String expression;

    private AspectJExpressionPointcut pointcut;

    public AspectJExpressionPointcutAdvisor() {
    }

    public AspectJExpressionPointcutAdvisor(String expression) {
        this.expression = expression;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        if (pointcut == null) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }
}
