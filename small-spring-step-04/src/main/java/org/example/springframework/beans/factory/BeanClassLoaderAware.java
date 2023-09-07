package org.example.springframework.beans.factory;

import org.example.springframework.beans.BeansException;

public interface BeanClassLoaderAware extends Aware {

    void setBeanClassLoader(ClassLoader classLoader) throws BeansException;

}
