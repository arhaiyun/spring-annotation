package com.exodus.test;

import com.exodus.config.MainConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// https://www.bilibili.com/video/BV1oW41167AV?p=7

public class IOCTest_LifeCycle {


    @Test
    public void test01() {
        // 创建IOC容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
        System.out.println("容器创建完成...");
        applicationContext.close();
        // car destroy...
    }
}
