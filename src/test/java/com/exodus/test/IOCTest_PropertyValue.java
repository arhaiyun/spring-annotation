package com.exodus.test;

import com.exodus.bean.Person;
import com.exodus.config.MainConfigOfPropertyValues;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// https://www.bilibili.com/video/BV1oW41167AV?p=7

public class IOCTest_PropertyValue {

    // 创建IOC容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfPropertyValues.class);

    public void printBeans(AnnotationConfigApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }

    @Test
    public void test01() {
        System.out.println("容器创建完成...");
        printBeans(applicationContext);
        System.out.println("--------------------------------------------");
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);

        applicationContext.close();
    }
}
