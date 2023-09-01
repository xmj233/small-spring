package org.example.springframework.context;

import org.example.springframework.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     *
     * @throws BeansException BeansException
     */
    void refresh() throws BeansException;

}
