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
 *
 * 3.ApplicationListener:监听容器中发布的事件，完成事件驱动的开发
 *      public interface ApplicationListener<E extends ApplicationEvent> extends EventListener
 *      监听ApplicationEvent及其子事件
 *
 *      基于事件开发步骤：
 *      a.写一个监听器监听某个事件（ApplicationEvent及其子类）
 *      b.将监听器加入到容器
 *      c.只要是容器中有相关事件发布，就能监听到这个事件
 *          ContextRefreshedEvent：容器刷新完成（所有的bean都完全创建）会发布这个事件
 *          ContextClosedEvent：容器关闭发布事件
 *      d.自己发布一个事件：
 *          applicationContext.publishEvent()
 *
 *      原理：
 *          ContextRefreshedEvent,IOCTest_Ext$1,ContextClosedEvent
 *      1).ContextRefreshedEvent事件
 *          a.容器创建对象：refresh()
 *          b.finishRefresh() 容器刷新完成会发布ContextRefreshEvent事件
 *          [发布事件流程]
 *          c.publishEvent()
 *              1.获取事件的多播器 getApplicationEventMulticaster
 *              2.multicastEvent派发事件
 *              3.获取到所有ApplicationListener； getApplicationListeners(event, type)
 *                  for (final ApplicationListener<?> listener : getApplicationListeners(event, type)) {
 * 			        a.如果有Executor(executor = getTaskExecutor(); )支持,可以使用Executor进行异步派发
 * 			        b.否则同步的执行Listener方法：invokeListener(listener, event);
 * 			        c.拿到Listen回调onApplicationEvent(event);
 *
 *
 *
 *  [事件多播器<派发器> getApplicationEventMulticaster ]
 *      1.容器创建对象：refresh()
 *      2.initApplicationEventMulticaster 初始化ApplicationEventMulticaster
 *          a先去容器中看是否有APPLICATION_EVENT_MULTICASTER_BEAN_NAME的组件
 *              if (beanFactory.containsLocalBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME)) {
 * 			       this.applicationEventMulticaster =
 * 					    beanFactory.getBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);
 * 			    }
 * 			b.如果没有的话创建一个并加入到容器中
 *              this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
 *              我们就可以在其它组件要派发事件时候自动注入applicationEventMulticaster
 *
 *
 *  [如何知道容器中有哪些监听器呢？]
 *  1.容器创建对象refresh()
 *  2.registerListeners();
 *      从容器中拿到所有的监听器，把他们注册到applicationEventMulticaster中
 *      String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
 *      将listener注册到 ApplicationEventMulticaster中
 *      getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
 *
 *
 *
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
