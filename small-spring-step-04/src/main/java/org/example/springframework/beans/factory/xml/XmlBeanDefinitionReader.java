package org.example.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.PropertyValue;
import org.example.springframework.beans.factory.config.BeanDefinition;
import org.example.springframework.beans.factory.config.BeanReference;
import org.example.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.example.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.example.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.example.springframework.core.io.Resource;
import org.example.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        super(beanDefinitionRegistry);
    }

    public XmlBeanDefinitionReader(ResourceLoader resourceLoader, BeanDefinitionRegistry beanDefinitionRegistry) {
        super(resourceLoader, beanDefinitionRegistry);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) {
        try {
            try (InputStream in = resource.getInPutStream()) {
                doLoadBeanDefinitions(in);
            }
        } catch (IOException | ClassNotFoundException | DocumentException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    private void doLoadBeanDefinitions(InputStream in) throws ClassNotFoundException, DocumentException {

        SAXReader reader = new SAXReader();
        org.dom4j.Document document = reader.read(in);
        Element root = document.getRootElement();

        Element componentScan = root.element("component-scan");

        if (componentScan != null) {
            String basePackage = componentScan.attributeValue("base-package");
            if (StrUtil.isEmpty(basePackage)) {
                throw new BeansException("The value of base-package attribute can not be empty or null");
            }
            scanPackages(basePackage);
        }


        List<Element> beanList = root.elements("bean");
        for (Element bean : beanList) {

            String id = bean.attributeValue("id");
            String name = bean.attributeValue("name");
            String className = bean.attributeValue("class");
            String initMethodName = bean.attributeValue("init-method");
            String destroyMethodName = bean.attributeValue("destroy-method");
            String scope = bean.attributeValue("scope");
            Class<?> clazz = Class.forName(className);

            String beanName = id != null ? id : name;

            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            beanDefinition.setInitMethodName(initMethodName);

            if (StrUtil.isNotEmpty(scope)) {
                beanDefinition.setScope(scope);
            }

            List<Element> propertyList = bean.elements("property");
            for (Element property : propertyList) {

                String attrName = property.attributeValue("name");
                String attrValue = property.attributeValue("value");
                String attref = property.attributeValue("ref");

                Object value = StrUtil.isNotEmpty(attref) ? new BeanReference(attref) : attrValue;

                PropertyValue propertyValue = new PropertyValue(attrName, value);

                beanDefinition.getPropertyValues().addPropertyValues(propertyValue);
            }

            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }

            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

    private void scanPackages(String basePackage) {
        String[] basePackageList = StrUtil.split(basePackage, ",");
        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner(getRegistry());
        classPathBeanDefinitionScanner.doScan(basePackageList);
    }
}
