package org.example.springframework.context;

import org.example.springframework.beans.factory.HierarchicalBeanFactory;
import org.example.springframework.beans.factory.ListableBeanFactory;
import org.example.springframework.core.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
