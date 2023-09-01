package org.example.springframework.beans.factory.config;

import org.example.springframework.beans.BeansException;

public interface BeanPostProcessor {

    /**
     * bean初始化之前
     *
     * @param bean bean
     * @param beanName beanName
     * @return bean
     * @throws BeansException BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * bean初始化之后，执行此方法
     *
     * @param bean bean
     * @param beanName beanName
     * @return bean
     * @throws BeansException BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
