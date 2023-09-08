package org.example.springframework.beans;

import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;
import org.example.springframework.beans.factory.FactoryBean;

import java.util.HashMap;
import java.util.Map;

public class ProxyBeanFactory implements FactoryBean<IDao> {

    @Override
    public IDao getObject() throws Exception {
        InvocationHandler handler = (proxy, method, args) -> {

            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("10001", "小傅哥");
            hashMap.put("10002", "八杯水");
            hashMap.put("10003", "阿毛");
            
            return "你被代理了 " + method.getName() + "：" + hashMap.get(args[0].toString());
        };
        return (IDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IDao.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}