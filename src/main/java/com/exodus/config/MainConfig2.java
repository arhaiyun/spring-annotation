package com.exodus.config;

import com.exodus.bean.Person;
import com.exodus.condition.LinuxCondition;
import com.exodus.condition.WindowsCondition;
import org.springframework.context.annotation.*;

@Configuration
public class MainConfig2 {
    /**
     * Scope默认为singleton,ioc容器启动会调用方法创建对象到ioc,以后获取都是直接从容器中获取（map.get()）
     * prototype 多实例：ioc容器启动的时候并不会调用方法创建对象，每次获取的时候才会调用方法创建对象
     *
     * @Lazy 单例模式下使用lazy注解，ioc容器启动的时候不创建对象，在第一次调用的时候创建对象
     */
    @Bean("person")
    @Scope
//    @Scope(value = "prototype")
    @Lazy
    public Person person() {
        System.out.println("给容器中添加person对象");
        return new Person("Penny", 30);
    }

    /**
     * @Conditional 如果是Windows系统注册bill
     * 如果是Linux系统注册linus
     */
    @Conditional({WindowsCondition.class})
    @Bean("bill")
    public Person person01() {
        return new Person("Bill Gate", 60);
    }

    @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person person02() {
        return new Person("Linus", 50);
    }
}
