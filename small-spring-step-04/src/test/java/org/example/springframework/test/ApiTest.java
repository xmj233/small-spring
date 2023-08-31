package org.example.springframework.test;

import cn.hutool.core.io.IoUtil;
import org.example.springframework.beans.UserService;
import org.example.springframework.beans.factory.support.BeanDefinitionReader;
import org.example.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.example.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.example.springframework.core.io.DefaultResourceLoader;
import org.example.springframework.core.io.Resource;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ApiTest {

    @Test
    public void classPathResourceTest() throws IOException {
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();

        Resource resource = defaultResourceLoader.getResource("classpath:spring.xml");

        InputStream inPutStream = resource.getInPutStream();

        String content = IoUtil.read(inPutStream, "UTF-8");

        System.out.println(content);

    }

    @Test
    public void systemFileResourceTest() throws IOException {
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();

        Resource resource = defaultResourceLoader.getResource("E:\\IdeaProjects\\small-spring\\small-spring-step-03\\src\\main\\resources\\spring.xml");

        InputStream inPutStream = resource.getInPutStream();

        String content = IoUtil.read(inPutStream, "UTF-8");

        System.out.println(content);

    }

    @Test
    public void testXml() {
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();

        Resource resource = defaultResourceLoader.getResource("classpath:spring.xml");

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        beanDefinitionReader.loadBeanDefinitions(resource);

        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }
}
