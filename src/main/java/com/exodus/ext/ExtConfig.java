package com.exodus.ext;

import com.exodus.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring扩展原理；
 *
 * 1.BeanPostProcessor: bean后置处理器，在bean创建对象初始化前后进行拦截工作的
 *      BeanFactoryPostProcessor: 这是beanFactory的后置处理器
 *      在BeanFactory标准初始化之后调用：所有的bean定义已经保存到BeanFactory 但是bean的实例还没创建
 *
 *  BeanFactoryPostProcessor原理
 *      1). IOC容器创建对象
 *      2). invokeBeanFactoryPostProcessors(beanFactory); 执行 BeanFactoryPostProcessors
 *          如何找到所有的BeanFactoryPostProcessors并执行它的方法
 *          a.直接在BeanFactory中找到所有类型是BeanFactoryPostProcessor的组件并执行它的方法
 *          b.在初始化创建其它组件前执行
 *
 * 2.BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 *      postProcessBeanDefinitionRegistry();
 *      在所有的bean定义信息将要被加载，bean实例还未创建的时候
 *      优先于 BeanFactoryPostProcessor执行，可以利用BeanDefinitionRegistryPostProcessor给容器中额外添加组件
 *
 *      原理：
 *          a.ioc创建对象
 *          b.refresh() -> invokeBeanFactoryPostProcessors(beanFactory);
 *          c.从容器中获取到所有的BeanDefinitionRegistryPostProcessor 组件，
 *              1).依次触发postProcessBeanDefinitionRegistry
 *              2).再来触发postProcessBeanFactory()方法
 *          d.再从容器中找到BeanFactoryPostProcessor，然后依次触发postProcessBeanFactory()方法
 *
 */

@ComponentScan("com.exodus.ext")
@Configuration
public class ExtConfig {

    @Bean
    public Car car() {
        return new Car();
    }

}
