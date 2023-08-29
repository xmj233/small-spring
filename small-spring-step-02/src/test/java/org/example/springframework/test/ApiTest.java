package org.example.springframework.test;

import org.example.springframework.beans.PropertyValue;
import org.example.springframework.beans.UserDao;
import org.example.springframework.beans.UserService;
import org.example.springframework.beans.PropertyValues;
import org.example.springframework.beans.factory.config.BeanDefinition;
import org.example.springframework.beans.factory.config.BeanReference;
import org.example.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy;
import org.example.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.junit.Test;

public class ApiTest {

    @Test
    public void beanDefinitionTest() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        beanFactory.registerBeanDefinition("userService", new BeanDefinition(UserService.class));

        UserService userService = (UserService) beanFactory.getBean("userService");

        userService.queryUserInfo();

        Object userService1 = beanFactory.getBean("userService");

        System.out.println(userService1);
        System.out.println(userService);

    }

    @Test
    public void instantiationTest() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        beanFactory.setInstantiationStrategy(new CglibSubclassingInstantiationStrategy());

        beanFactory.registerBeanDefinition("userService", new BeanDefinition(UserService.class));

        UserService userService = (UserService) beanFactory.getBean("userService", "zs");

        userService.queryUserInfo();
    }

    @Test
    public void propertyValueTest() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValues(new PropertyValue("uid", "10001"));
        propertyValues.addPropertyValues(new PropertyValue("userDao", new BeanReference("userDao")));

        beanFactory.registerBeanDefinition("userService", new BeanDefinition(UserService.class, propertyValues));

        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }
}
