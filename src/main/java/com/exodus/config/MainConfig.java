package com.exodus.config;

import com.exodus.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Configuration
// ComponentScan 指定要扫描的包
// excludeFilters, includeFilters
// useDefaultFilters
// ComponentScans
@ComponentScan(value = "com.exodus", includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
}, useDefaultFilters = false)
public class MainConfig {
    @Bean("Person")
    public Person person01() {
        return new Person("Henry", 30);
    }
}
