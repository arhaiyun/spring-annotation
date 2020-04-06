package com.exodus.test;

import com.exodus.bean.Yellow;
import com.exodus.config.MainConfigOfProfile;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

// https://www.bilibili.com/video/BV1oW41167AV?p=24

public class IOCTest_Profile {

    /**
     * 1.使用命令行动态参数: 在虚拟机参数位置加载：-Dspring.profiles.active=test/dev/prod中的一种开启相应的环境设置
     * 2.
     *
     */
    @Test
    public void test01() {
//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfProfile.class);

        // 1.通过无参构造器的方式创建一个applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 2.设置需要激活的环境
        applicationContext.getEnvironment().setActiveProfiles("test");
        // 3.注册主配置类
        applicationContext.register(MainConfigOfProfile.class);
        // 4.启动刷新容器
        applicationContext.refresh();

        String[] beanNamesForType = applicationContext.getBeanNamesForType(DataSource.class);
        for(String name : beanNamesForType) {
            System.out.println(name);
        }

        Yellow yellow = applicationContext.getBean(Yellow.class);
        System.out.println(yellow);
        applicationContext.close();
    }
}
