package com.exodus.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WindowsCondition implements Condition {
    /**
     * ConditionContext 判断条件能使用的上下文环境
     * AnnotatedTypeMetadata 注释信息
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        // 获取IOC使用的beanFactory
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
        // 获取类加载器
        ClassLoader classLoader = conditionContext.getClassLoader();
        // 获取bean定义的注册类
        BeanDefinitionRegistry registry = conditionContext.getRegistry();
        // 获取当前环境信息
        Environment environment = conditionContext.getEnvironment();
        String property = environment.getProperty("os.name");

        // 可以判断容器中bean的注册情况，也可以给容器中注册bean
        boolean person = registry.containsBeanDefinition("person");


        return property.contains("Windows");
    }
}
