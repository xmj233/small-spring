package org.example.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.PropertyValue;
import org.example.springframework.beans.PropertyValues;
import org.example.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.example.springframework.beans.factory.config.BeanDefinition;
import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;
import java.util.List;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition, Object... args) throws BeansException {
        Object bean = null;

        try {
            bean = createBeanInstance(beanDefinition, args);

            applyPropertyValues(name, beanDefinition, bean);

            bean = initializeBean(name, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("instantiation of bean failed", e);
        }

        addSingleton(name, bean);
        return bean;
    }

    private void applyPropertyValues(String name, BeanDefinition beanDefinition, Object bean) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();

            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String propertyValueName = propertyValue.getName();
                Object propertyValueValue = propertyValue.getValue();

                if (propertyValueValue instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) propertyValueValue;
                    propertyValueValue = getBean(beanReference.getBeanName());
                }
                BeanUtil.setFieldValue(bean, propertyValueName, propertyValueValue);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values: " + name);
        }
    }

    private Object createBeanInstance(BeanDefinition beanDefinition, Object[] args) {
        Constructor ctorToUse = null;

        Class clazz = beanDefinition.getBeanClass();
        Constructor[] constructors = clazz.getConstructors();

        for (Constructor ctor : constructors) {
            if (args != null && ctor.getParameterTypes().length == args.length) {
                ctorToUse = ctor;
                break;
            }
        }
        return instantiationStrategy.instantiate(beanDefinition, ctorToUse, args);
    }

    private Object initializeBean(String name, Object bean, BeanDefinition beanDefinition) {
        // 1. 执行 BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, name);

        // 待完成内容：invokeInitMethods(beanName, wrappedBean, beanDefinition);
        invokeInitMethods(name, wrappedBean, beanDefinition);

        // 2. 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, name);
        return wrappedBean;
    }

    private void invokeInitMethods(String name, Object wrappedBean, BeanDefinition beanDefinition) {

    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;

        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;

        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }
}
