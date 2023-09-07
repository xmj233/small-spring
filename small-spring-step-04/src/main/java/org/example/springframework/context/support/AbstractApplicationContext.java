package org.example.springframework.context.support;

import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.example.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.example.springframework.beans.factory.config.BeanPostProcessor;
import org.example.springframework.context.ConfigurableApplicationContext;
import org.example.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {


    @Override
    public void refresh() throws BeansException {
        // 创建bean工厂并将bean注入spring容器
        refreshBeanFactory();

        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 注册applicationContextAwareProcessor, 使实现ApplicationContextAware的bean都能感知到所属的ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 执行beanFactoryPostProcessor
        invokeBeanFactoryPostProcessor(beanFactory);

        // 注册beanPostProcessor
        registerBeanPostProcessor(beanFactory);

        // 实例化bean
        beanFactory.preInstantiateSingletons();
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        getBeanFactory().destroySingletons();
    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);

        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);

        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }


    @Override
    public <T> T getBean(String name, Class<T> requireType) throws BeansException {
        return getBeanFactory().getBean(name, requireType);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanDefinitionNames();
    }
}
