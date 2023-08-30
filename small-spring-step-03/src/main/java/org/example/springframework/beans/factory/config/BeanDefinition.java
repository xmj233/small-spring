package org.example.springframework.beans.factory.config;

import org.example.springframework.beans.PropertyValues;

public class BeanDefinition {

    private Class beanDefinition;

    private PropertyValues propertyValues;

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public BeanDefinition(Class beanDefinition, PropertyValues propertyValues) {
        this.beanDefinition = beanDefinition;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public BeanDefinition(Class beanDefinition) {
        this.beanDefinition = beanDefinition;
        this.propertyValues = new PropertyValues();
    }

    public Class getBeanDefinition() {
        return beanDefinition;
    }
}
