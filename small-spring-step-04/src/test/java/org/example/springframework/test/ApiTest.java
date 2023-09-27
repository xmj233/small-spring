package org.example.springframework.test;

import cn.hutool.core.io.IoUtil;
import org.example.springframework.aop.*;
import org.example.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.example.springframework.aop.framework.Cglib2AopProxy;
import org.example.springframework.aop.framework.JdkDynamicAopProxy;
import org.example.springframework.beans.*;
import org.example.springframework.beans.factory.support.BeanDefinitionReader;
import org.example.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.example.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.example.springframework.context.support.ClassPathXmlApplicationContext;
import org.example.springframework.core.io.DefaultResourceLoader;
import org.example.springframework.core.io.Resource;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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

    @Test
    public void test_event() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-event.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1019129009086763L, "成功了！"));

        applicationContext.registerShutdownHook();
    }


    @Test
    public void test_aop() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* org.example.springframework.aop.RoleService.*(..))\"");


        Class<RoleService> roleServiceClass = RoleService.class;

        boolean matches = pointcut.matches(roleServiceClass);
        System.out.println(matches);

        Method queryUserInfo = roleServiceClass.getDeclaredMethod("addRole");
        boolean matches1 = pointcut.matches(queryUserInfo, roleServiceClass);
        System.out.println(matches1);


    }

    @Test
    public void test_proxy_class() {
        IRoleService userService = (IRoleService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IRoleService.class}, (proxy, method, args) -> "你被代理了！");
        String result = userService.addRole();
        System.out.println("测试结果：" + result);

    }


    @Test
    public void test_jdk_dynamic_proxy() {
        IRoleService roleService = new RoleService();

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* org.example.springframework.aop.IRoleService.*(..))\"");

        AdviceSupport adviceSupport = new AdviceSupport();

        adviceSupport.setMethodInterceptor(new RoleMethodInterceptor());
        adviceSupport.setMethodMatcher(pointcut);
        adviceSupport.setTargetSource(new TargetSource(roleService));

        JdkDynamicAopProxy jdkDynamicAopProxy = new JdkDynamicAopProxy(adviceSupport);

        IRoleService proxy = (IRoleService) jdkDynamicAopProxy.getProxy();
        String s = proxy.addRole();
        System.out.println("测试结果: " + s);

        String role = proxy.getRole();
        System.out.println("测试结果: " + role);
    }

    @Test
    public void test_cglib2_aop_proxy() {
        IRoleService roleService = new RoleService();

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* org.example.springframework.aop.IRoleService.*(..))\"");

        AdviceSupport adviceSupport = new AdviceSupport();

        adviceSupport.setMethodInterceptor(new RoleMethodInterceptor());
        adviceSupport.setMethodMatcher(pointcut);
        adviceSupport.setTargetSource(new TargetSource(roleService));

        Cglib2AopProxy cglib2AopProxy = new Cglib2AopProxy(adviceSupport);

        IRoleService proxy = (IRoleService) cglib2AopProxy.getProxy();
        String s = proxy.addRole();
        System.out.println("测试结果: " + s);

        String role = proxy.getRole();
        System.out.println("测试结果: " + role);
    }

    @Test
    public void test_aop_() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-aop2.xml");
        IRoleService roleService = applicationContext.getBean("roleService", IRoleService.class);
        roleService.addRole();
    }
}
