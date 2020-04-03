package com.exodus.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinuxCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
        ClassLoader classLoader = conditionContext.getClassLoader();
        Environment environment = conditionContext.getEnvironment();
        BeanDefinitionRegistry registry = conditionContext.getRegistry();
        String property = environment.getProperty("os.name");

        return property.contains("Linux");
    }
}
