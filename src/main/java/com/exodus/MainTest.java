package com.exodus;

import com.exodus.bean.Person;
import com.exodus.config.MainConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        Person bean = applicationContext.getBean(Person.class);
        System.out.println(bean);
        String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
        for (String string : namesForType) {
            System.out.println(string);
        }
    }
}
