package org.example.springframework.aop.framework;

import org.example.springframework.aop.AdviceSupport;

public class ProxyFactory {
    private AdviceSupport adviceSupport;

    public ProxyFactory(AdviceSupport adviceSupport) {
        this.adviceSupport = adviceSupport;
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if (adviceSupport.isProxyTargetClass()) {
            return new Cglib2AopProxy(adviceSupport);
        }
        return new JdkDynamicAopProxy(adviceSupport);
    }
}
