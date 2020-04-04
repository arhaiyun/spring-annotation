package com.exodus.condition;

import com.exodus.bean.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     *
     * @param annotationMetadata 当前类的注解信息
     * @param beanDefinitionRegistry BeanDefinition注册类
     *                               将所有需要添加到容器中的bean，调用BeanDefinitionRegistry.registerBeanDefinition 手动注册
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        boolean red = beanDefinitionRegistry.containsBeanDefinition("com.exodus.bean.Red");
        boolean yellow = beanDefinitionRegistry.containsBeanDefinition("com.exodus.bean.Yellow");

        if (red && yellow) {
            RootBeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);
            // 注册一个bean指定bean的名字
            beanDefinitionRegistry.registerBeanDefinition("rainBow", beanDefinition);
        }

    }
}
