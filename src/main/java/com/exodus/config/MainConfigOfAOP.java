package com.exodus.config;

import com.exodus.aop.LogAspects;
import com.exodus.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP: [动态代理]
 *      指在程序运行期间动态的将某段代码切入到指定方法位置进行运行的编程方式
 *      1.导入aop模块 SpringAOP (spring-aspects)
 *      2.定义一个业务逻辑类(MathCalculator)
 *              在业务逻辑运行的时候进行日志打印（方法之前、方法运行结束、方法出现异常）
 *      3.定义日志切面类(LogAspects) 切面里面的方法需要动态感知MathCalculator.div运行到什么位置
 *          @Aspect 告诉Spring这是一个切面类,
 *          通知方法：
 *              a.前置通知(@Before) logStart: 在目标方法(div)运行之前运行
 *              b.后置通知(@After) logEnd: 在目标方法(div)运行之后运行，无论正常结束还是异常结束都会调用
 *              c.返回通知(@AfterReturning) logReturn: 在目标方法(div)正常返回之后运行
 *              d.异常通知(@AfterThrowing) logException: 在目标方法(div)运行异常之后运行
 *              e.环绕通知(@Around) 动态代理，手动推进目标方法运行(jointPoint.proceed())
 *      4.给切面类的目标方法标注何时何地运行（通知注解）
 *      5.将切面类和业务逻辑类（目标方法所在类）都加入到容器中
 *      6.必须告诉Spring哪个类是切面类（给切面类加一个注解@Aspect）
 *    ☆7.给配置类中添加 @EnableAspectJAutoProxy 开启基于注解的AOP模式
 *          Spring中有很多的@EnableXXX
 *
 *  AOP分三步操作：
 *  1. 将业务逻辑组件以及切面类都加入到容器中，通过@Aspect告诉Spring那个是切面类
 *  2. 在切面类的每一个通知方法上标注通知注解，告诉Spring何时何地运行该方法（注意切入点表达式的书写方式）
 *  3. 在配置类(MainConfigOfAOP) 中通过@EnableAspectJAutoProxy 开启基于注解的AOP模式
 *
 *
 *  AOP原理： [看给容器中注册了什么组件，以及这些组件的作用是什么]
 *      @EnableAspectJAutoProxy
 *  1.@EnableAspectJAutoProxy 是什么？
 *      @Import(AspectJAutoProxyRegistrar.class) 给容器中导入 AspectJAutoProxyRegistrar
 *      利用 AspectJAutoProxyRegistrar 自定义给容器中注册bean
 *      internalAutoProxyCreator=AnnotationAwareAspectJAutoProxyCreator
 *
 *      给容器中注册一个 AnnotationAwareAspectJAutoProxyCreator
 *
 *  2.AnnotationAwareAspectJAutoProxyCreator
 *      ---> AspectJAwareAdvisorAutoProxyCreator
 *          ---> AbstractAdvisorAutoProxyCreator
 *              ---> AbstractAutoProxyCreator
 * 		                implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 * 		                关注后置处理器的工作（在bean初始化完成前后做的事情）
 *
 *
 */

@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAOP {

    // 将业务逻辑类加载到容器中
    @Bean
    public MathCalculator calculator() {
        return new MathCalculator();
    }

    // 将切面类加载到容器中
    @Bean
    public LogAspects logAspects() {
        return new LogAspects();
    }

}
