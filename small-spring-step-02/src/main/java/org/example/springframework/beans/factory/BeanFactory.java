package org.example.springframework.beans.factory;

public interface BeanFactory {

    Object getBean(String name);

    Object getBean(String name, Object... args);
}
