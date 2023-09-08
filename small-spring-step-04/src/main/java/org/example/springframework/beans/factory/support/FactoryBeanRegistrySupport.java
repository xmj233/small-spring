package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getCachedObjectFromFactoryBean(String beanName) {
        Object factoryBean = factoryBeanObjectCache.get(beanName);
        return factoryBean != NULL_OBJECT ? factoryBean : null;
    }

    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName) {
        if (factory.isSingleton()) {
            Object object = factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factory, beanName);
                // NULL_OBJECT代表FactoryBean的getObject方法返回为null
                factoryBeanObjectCache.put(beanName, object != null ? object : NULL_OBJECT);
            }
            return object != NULL_OBJECT ? object : null;
        }
        else {
            return doGetObjectFromFactoryBean(factory, beanName);
        }
    }

    private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String beanName){
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }

}
