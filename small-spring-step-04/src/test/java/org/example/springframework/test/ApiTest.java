package org.example.springframework.test;

import cn.hutool.core.io.IoUtil;
import org.example.springframework.beans.MyBeanFactoryPostProcessor;
import org.example.springframework.beans.MyBeanPostProcessor;
import org.example.springframework.beans.Service;
import org.example.springframework.beans.UserService;
import org.example.springframework.beans.factory.support.BeanDefinitionReader;
import org.example.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.example.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.example.springframework.context.support.ClassPathXmlApplicationContext;
import org.example.springframework.core.io.DefaultResourceLoader;
import org.example.springframework.core.io.Resource;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ApiTest {
    @Test
    public void testBeanFactoryPostProcessorAndBeanPostProcessor() {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        MyBeanFactoryPostProcessor myBeanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        myBeanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        MyBeanPostProcessor myBeanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(myBeanPostProcessor);

        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }

    @Test
    public void testXml() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        UserService userService = applicationContext.getBean("userService", UserService.class);

        userService.queryUserInfo();

        System.out.println("userService.ApplicationContext= " + userService.getApplicationContext());
        System.out.println("userService.BeanFactory= " + userService.getBeanFactory());

    }

    @Test
    public void testPrototype() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        UserService userService1 = applicationContext.getBean("userService", UserService.class);
        UserService userService2 = applicationContext.getBean("userService", UserService.class);

        System.out.println("userService1 = " + userService1);
        System.out.println("userService2 = " + userService2);
    }

    @Test
    public void test_factoryBean() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-factorybean.xml");

        Service service = applicationContext.getBean("service", Service.class);
        String s = service.queryUserInfo();
        System.out.println(s);
    }
}
