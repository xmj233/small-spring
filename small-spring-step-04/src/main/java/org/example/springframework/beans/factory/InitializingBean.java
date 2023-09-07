package org.example.springframework.beans.factory;

public interface InitializingBean {

    /**
     * 属性填充后调用
     */
    void afterPropertiesSet() throws Exception;

}
