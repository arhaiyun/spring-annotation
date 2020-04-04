package com.exodus.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Dog implements ApplicationContextAware {

    ApplicationContext applicationContext;

    public Dog() {
        System.out.println("dog constructor...");
    }

    @PostConstruct
    public void init() {
        System.out.println("dog post PostConstruct..");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("dog PreDestroy..");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
