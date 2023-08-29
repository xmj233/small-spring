package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.factory.BeanFactory;
import org.example.springframework.beans.factory.config.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String name) {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) {
        return doGetBean(name, args);
    }


    // 这里加 泛型， 在调用方就不需要进行强制类型转换了 例如 User bean = doGetBean(name, args); 如果没有泛型， 则是  User bean = (user) doGetBean(name, args);
    private <T> T doGetBean(final String name, final Object... args) {
        Object singleton = getSingleton(name);
        if (singleton != null) {
            return (T) singleton;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return (T) createBean(name, beanDefinition, args);
    }

    abstract protected BeanDefinition getBeanDefinition(String name);

    abstract protected Object createBean(String name, BeanDefinition beanDefinition, Object... args);
}
