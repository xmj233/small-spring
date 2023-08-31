package org.example.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.PropertyValue;
import org.example.springframework.beans.PropertyValues;
import org.example.springframework.beans.factory.config.BeanDefinition;
import org.example.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

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
}
