package org.example.springframework.beans.factory;

import org.example.springframework.beans.BeansException;

public interface BeanNameAware extends Aware{

    void setBeanName(String beanName) throws BeansException;

}
