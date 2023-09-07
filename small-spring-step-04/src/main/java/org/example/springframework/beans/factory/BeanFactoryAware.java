package org.example.springframework.beans.factory;

import org.example.springframework.beans.BeansException;

public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
