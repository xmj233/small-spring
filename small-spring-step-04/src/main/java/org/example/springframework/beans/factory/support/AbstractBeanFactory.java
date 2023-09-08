package org.example.springframework.beans.factory.support;

import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.factory.BeanFactory;
import org.example.springframework.beans.factory.FactoryBean;
import org.example.springframework.beans.factory.config.BeanDefinition;
import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.example.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requireType) throws BeansException {
        return (T) getBean(name);
    }

    // 这里加 泛型， 在调用方就不需要进行强制类型转换了 例如 User bean = doGetBean(name, args); 如果没有泛型， 则是  User bean = (user) doGetBean(name, args);
    private <T> T doGetBean(final String name, final Object... args) {
        Object singleton = getSingleton(name);
        if (singleton != null) {
            return (T) getObjectForBeanInstance(singleton, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, name);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        Object object = getCachedObjectFromFactoryBean(beanName);

        if (object == null) {
            object = getObjectFromFactoryBean((FactoryBean<?>) beanInstance, beanName);
        }

        return object;
    }

    abstract protected BeanDefinition getBeanDefinition(String name);

    abstract protected Object createBean(String name, BeanDefinition beanDefinition, Object... args);

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }
}
