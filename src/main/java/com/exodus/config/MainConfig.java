package com.exodus.config;

import com.exodus.bean.Person;
import com.exodus.service.BookService;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Configuration
// ComponentScan 指定要扫描的包
// excludeFilters, includeFilters
// useDefaultFilters
// ComponentScans
//@ComponentScan(value = "com.exodus", includeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
//}, useDefaultFilters = false)
//
@ComponentScans(value = {
        @ComponentScan(value = "com.exodus", includeFilters = {
//                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
//                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class}),
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
        }, useDefaultFilters = false)
})
public class MainConfig {
    @Bean("person")
    public Person person01() {
        return new Person("Henry", 30);
    }
}
