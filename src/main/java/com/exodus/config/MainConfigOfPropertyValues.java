package com.exodus.config;

import com.exodus.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
// 使用@Properties读取外部配置文件中的k/v保存到运行时的环境变量中
@PropertySource(value={"classpath:person.properties"}, ignoreResourceNotFound = true)
//@ComponentScan("com.exodus.bean")
public class MainConfigOfPropertyValues {

    @Bean
    public Person person() {
        return new Person();
    }
}
