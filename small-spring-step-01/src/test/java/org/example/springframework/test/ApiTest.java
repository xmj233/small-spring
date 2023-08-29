package org.example.springframework.test;

import org.example.springframework.BeanDefinition;
import org.example.springframework.BeanFactory;
import org.example.springframework.bean.UserService;
import org.junit.Test;

public class ApiTest {


    @Test
    public void testBeanFactory() {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.registerBeanDefinition("userService", new BeanDefinition(new UserService()));

        UserService userService = (UserService) beanFactory.getBean("userService");

        userService.queryUserInfo();
    }
}
