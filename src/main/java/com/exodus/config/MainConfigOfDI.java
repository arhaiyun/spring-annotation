package com.exodus.config;

import com.exodus.bean.Color;
import com.exodus.bean.ColorFactoryBean;
import com.exodus.bean.Person;
import com.exodus.bean.Red;
import com.exodus.condition.LinuxCondition;
import com.exodus.condition.MyImportBeanDefinitionRegistrar;
import com.exodus.condition.MyImportSelector;
import com.exodus.condition.WindowsCondition;
import org.springframework.context.annotation.*;

@Conditional({WindowsCondition.class}) // 类中组件统一设置：满足当前条件，这个类中配置的所有bean注册才能生效。
@Configuration
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class}) //
public class MainConfigOfDI {
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

    /**
     * 给容器中注册组件的4种方式：
     *
     * 1). 包扫描 + 组件标注注解(@Controller/@Service/@Repository/@Component) ----> 局限于自己写的类
     * 2). 使用@Bean -----> 可以导入第三方包里面的组件
     * 3). @Import -----> 快速给容器中导入一个组件
     *          1. @Import(要导入到容器中的组件)
     *          2. ImportSelector 返回要导入的组件的全类名数组
     *          3. ImportBeanDefinitionRegistrar 手动注册到bean容器中
     * 4). 使用Spring提供的FactoryBean
     *          1.默认获取到的是工厂bean调用getObject创建的对象
     *          2.如果要获取bean本身，则需要在id前面加一个& : &colorFactoryBean
     * */
    @Bean
    public ColorFactoryBean colorFactoryBean() {
        return new ColorFactoryBean();
    }
}
