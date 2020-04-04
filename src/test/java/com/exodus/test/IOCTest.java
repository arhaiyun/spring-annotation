package com.exodus.test;

import com.exodus.bean.Person;
import com.exodus.bean.Red;
import com.exodus.config.MainConfig;
import com.exodus.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

// https://www.bilibili.com/video/BV1oW41167AV?p=7

public class IOCTest {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

    @SuppressWarnings("resource")
    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }

    @Test
    public void test02() {
        // 默认都是单例
        Object person = applicationContext.getBean("person");
        Object person2 = applicationContext.getBean("person");
        System.out.println(person == person2);
    }

    @Test
    public void test03() {
        // 获取环境变量信息
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        System.out.println(environment.getProperty("os.name"));

        String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
        for (String string : namesForType) {
            System.out.println(string);
        }

        Map<String, Person> persons = applicationContext.getBeansOfType(Person.class);
        System.out.println(persons);
    }

    public void printBeans(AnnotationConfigApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for(String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }

    @Test
    public void testImport() {
        printBeans(applicationContext);
        Red bean = applicationContext.getBean(Red.class);
        System.out.println(bean);

        // 获取的是colorFactoryBean.getObject方法返回对象
        Object bean1 = applicationContext.getBean("colorFactoryBean");
        System.out.println(bean1.getClass());
        Object bean2 = applicationContext.getBean("colorFactoryBean");
        // colorFactoryBean.isSingleton 返回为true则一致
        System.out.println(bean1 == bean2);

        Object colorFactoryBean = applicationContext.getBean("&colorFactoryBean");
        System.out.println(colorFactoryBean.getClass());
//        class com.exodus.bean.ColorFactoryBean
    }
}
