package com.exodus.condition;

import com.exodus.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * bean的生命周期：
 * bean创建  ----  初始化 ---- 销毁的过程
 * 容器管理bean的生命周期
 * 我们可以自定义初始化和销毁方法：容器在bean进行到当前生命周期的时候来调用我们自定义的初始化和销毁方法
 *
 * 构造（对象创建）
 *      单实例：在容器启动的时候创建对象
 *      多实例：在每次获取的时候创建对象
 *
 * BeanPostProcessor.postProcessBeforeInitialization
 * 初始化：
 *      对象创建好，并赋值好，调用初始化方法
 * BeanPostProcessor.postProcessAfterInitialization
 *
 * 销毁：
 *      单实例：容器关闭的时候
 *      多实例：容器不会管理这个bean, 所以也不会调用销毁方法
 *
 * 遍历得到容器中所有的BeanPostProcessor 然后挨个执行 beforeInitialization
 *
 *
 * BeanPostProcessor原理
 *
 * doCreateBean：
 * 1.populateBean(beanName, mbd, instanceWrapper);
 * 2.initializeBean {
 *      applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 *      invokeInitMethods(beanName, wrappedBean, mbd);
 *      applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 * }
 *
 * 1.制定初始化和销毁方法
 *      @Bean(initMethod = "init", destroyMethod = "destroy")
 *
 * 2.通过让Bean实现InitializingBean(定义初始化逻辑),
 *          DisposableBean(定义销毁逻辑)
 *
 * 3.可以使用 JSR250:
 *          @PostConstruct: 在bean创建完成并且属性赋值完成来执行初始化方法
 *          @PreDestroy:  在容器销毁bean之前通知我们做的清理工作
 *
 * 4. BeanPostProcessor[interface] bean的后置处理器
 *          postProcessBeforeInitialization
 *          postProcessAfterInitialization
 *
 * Spring底层对BeanPostProcessor的使用
 *          bean赋值，注入其它组件，@Autowired,生命周期注解，@Async
 *
 * @author arhaiyun
 */
@Configuration
@ComponentScan("com.exodus.bean")
public class MainConfigOfLifeCycle {
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Car car() {
        return new Car();
    }
}
