package com.exodus.test;

import com.exodus.bean.Boss;
import com.exodus.bean.Car;
import com.exodus.bean.Color;
import com.exodus.config.MainConfigOfAutowired;
import com.exodus.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// https://www.bilibili.com/video/BV1oW41167AV?p=7

public class IOCTest_Autowired {

    @Test
    public void test01() {
        // 创建IOC容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);
        BookService bean = applicationContext.getBean(BookService.class);
        System.out.println(bean);
//        BookDao bean1 = applicationContext.getBean(BookDao.class);
//        System.out.println(bean1);

        Boss boss = applicationContext.getBean(Boss.class);
        System.out.println(boss);

        Car car = applicationContext.getBean(Car.class);
        System.out.println(car);

        Color color = applicationContext.getBean(Color.class);
        System.out.println(color);

        System.out.println(applicationContext);

        applicationContext.close();
    }
}
