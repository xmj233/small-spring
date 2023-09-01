package org.example.springframework.beans.factory.config;

import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {

    /**
     * 所有的beanDefinition加载完成之后，bean实例化之前，提供修改BeanDefinition的机制
     *
     * @param beanFactory beanFactory
     * @throws BeansException BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
