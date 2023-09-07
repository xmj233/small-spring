package org.example.springframework.context;

import org.example.springframework.beans.BeansException;
import org.example.springframework.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
