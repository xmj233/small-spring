package org.example.springframework.beans;

import org.example.springframework.beans.factory.*;
import org.example.springframework.context.ApplicationContext;
import org.example.springframework.context.ApplicationContextAware;

public class UserService implements InitializingBean, DisposableBean, BeanClassLoaderAware, BeanNameAware, BeanFactoryAware, ApplicationContextAware {

    private String uid;

    private String company;

    private String location;

    private UserDao userDao;

    private ApplicationContext applicationContext;

    private BeanFactory beanFactory;

    public void setLocation(String location) {
        this.location = location;
    }

    public void queryUserInfo() {
        String userName = userDao.queryUserInfo(uid);
        System.out.println(userName + ", 公司：" + company + ", 地点：" + location);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("userService.destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("userService.afterPropertiesSet");
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) throws BeansException {
        System.out.println("classLoader = " + classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String beanName) throws BeansException {
        System.out.println("beanName = " + beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
