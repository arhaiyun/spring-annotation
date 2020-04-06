package com.exodus.test;

import com.exodus.aop.MathCalculator;
import com.exodus.config.MainConfig;
import com.exodus.config.MainConfigOfAOP;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// https://www.bilibili.com/video/BV1oW41167AV?p=27

public class IOCTest_AOP {

    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);

        //1.不要自己创建对象去测试aop功能，而是通过容器去获取组件信息
//        MathCalculator mathCalculator = new MathCalculator();
        MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);
        mathCalculator.div(1, 1);
//        mathCalculator.div(1, 0);

        applicationContext.close();
    }
}
