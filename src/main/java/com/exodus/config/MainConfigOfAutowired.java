package com.exodus.config;

import com.exodus.bean.Car;
import com.exodus.bean.Color;
import com.exodus.bean.Person;
import com.exodus.dao.BookDao;
import org.springframework.context.annotation.*;


/**
 *
 * 自动装配：
 *          Spring利用依赖注入（DI）完成对IOC容器中各个组件的依赖关系赋值
*     1. @Autowired 自动注入：
 *          1).默认优先按照类型去容器中找到相应的组件： applicationContext.getBean(BookDao.class); 找到之后就赋值
 *          2).如果找到多个相同类型的组件，再将属性的名称去作为组件的id去容器中查找 applicationContext.getBean("bookDao2")
 *          3).通过@Qualifier("bookDao")指定需要装配的组件的id
 *          4).自动装配则一定要将属性赋值好，没有就会报错，可以通过 @Autowired(required = false);屏蔽错误
 *          5).@Primary：让Spring进行自动装配的时候，默认使用首选的bean，也可以继续使用@Qualifier指定需要装配bean的名字
 *
 *          BookService {
 *              @Autowired
 *              BookDao bookDao;
 *          }
 *
 *    2.Spring还支持@Resource(JSR250)和@Inject(JSR330) -- [Java规范的注解]
 *          @Resouce: 可以跟Autowired一样实现自动装配，默认是按照组件名称进行装配的，没有能支持@Primary
 *          @Inject: 需要导入javax.inject包，与@Autowired一样的功能，没有required=false的功能
 *          @Autowired是spring框架，@Resource、@Inject是java规范
 *
 *          AutowiredAnnotationBeanPostProcessor
 *
 *   3. @Autowired注解可以使用在构造器、参数、方法以及属性上 (Boss.java)
 *          @Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
 *          1)标注在方法位置： @Bean+方法参数，参数从容器中获取； 默认不用写@Autowired效果一样都可以自动装配
 *          2)标注在构造器上，如果组件只有一个有参构造器，这个有参构造器的@Autowired可以省略，参数位置的组件还是能够自动从容器中获取
 *          3)标注在构造器或者Set方法参数位置
 *
 *   4.如果自定组件想使用Spring容器底层的一些组件（ApplicationContext,BeanFactory, xxx ...） (Red.java)
 *          只需要让自定义组件实现xxxAware接口：在创建对象的时候会调用接口规定的方法注入组件
 *          将Spring底层的一些组件注入到自定义的bean中
 *          xxxAware：功能使用xxxProcessor
 *          ApplicationContextAware ==> ApplicationContextAwareProcessor implements BeanPostProcessor
 *
 *
 *
 */

@Configuration
@ComponentScan({"com.exodus.controller", "com.exodus.service", "com.exodus.dao", "com.exodus.bean"})
public class MainConfigOfAutowired {

    @Primary
    @Bean("bookDao2")
    public BookDao bookDao() {
        BookDao bookDao = new BookDao();
        bookDao.setLabel("2");
        return bookDao;
    }

    /**
     * @Bean 标注的方法创建对象的时候，方法参数@param car从容器中获取
     * @param car
     * @return
     */
    @Bean
    public Color color(Car car) {
        Color color = new Color();
        color.setCar(car);
        return color;
    }
}
