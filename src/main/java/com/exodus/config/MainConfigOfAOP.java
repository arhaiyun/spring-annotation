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
 *  @EnableAspectJAutoProxy
 *
 *  1.@EnableAspectJAutoProxy 是什么？
 *      @Import(AspectJAutoProxyRegistrar.class) 给容器中导入 AspectJAutoProxyRegistrar
 *      利用 AspectJAutoProxyRegistrar 自定义给容器中注册bean : BeanDefinition
 *      internalAutoProxyCreator=AnnotationAwareAspectJAutoProxyCreator
 *
 *      给容器中注册一个 AnnotationAwareAspectJAutoProxyCreator
 *
 *  2.AnnotationAwareAspectJAutoProxyCreator
 *      ---> AspectJAwareAdvisorAutoProxyCreator
 *          ---> AbstractAdvisorAutoProxyCreator
 *              ---> AbstractAutoProxyCreator
 * 		                implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 * 		                关注后置处理器的工作（在bean初始化完成前后做的事情）、自动装配BeanFactory
 *
 *  AbstractAutoProxyCreator.setBeanFactory
 *  AbstractAutoProxyCreator中有后置处理器的逻辑
 *
 *  AbstractAdvisorAutoProxyCreator.setBeanFactory -> initBeanFactory()
 *
 *  AspectJAwareAdvisorAutoProxyCreator
 *
 *  AnnotationAwareAspectJAutoProxyCreator.initBeanFactory
 *
 *  流程分析
 *  1.创建ioc容器 new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
 *  2.注册配置类，调用refresh() 刷新容器
 *  3.registerBeanPostProcessors(beanFactory); 注册bean的后置处理器来方便拦截bean的创建
 *      1). 获取ioc容器已经定义了的需要创建对象的所有 BeanPostProcessor
 *      2). 给容器中加其他的 BeanPostProcessor
 *      3). 优先注册实现了 PriorityOrdered接口的 BeanPostProcessor
 *      4). 再给容器中注册了实现 Ordered接口的 BeanPostProcessor
 *      5). 注册没实现优先级接口的 BeanPostProcessor
 *      6). 注册BeanPostProcessor实际上就是创建BeanPostProcessor对象并保存在容器中
 *          创建internalAutoProxyCreator的BeanPostProcessor[AnnotationAwareAspectJAutoProxyCreator]
 *          a.创建Bean的实例
 *          b.populateBean给Bean的属性赋值
 *          c.initializeBean进行Bean的初始化操作
 *              1.invokeAwareMethods()，处理Aware接口的方法回调
 *              2.applyBeanPostProcessorsBeforeInitialization() 应用后置处理器postProcessBeforeInitialization方法
 *              3.invokeInitMethods() 执行自定义的初始化方法
 *              4.applyBeanPostProcessorsAfterInitialization() 应用后置处理器postProcessAfterInitialization方法
 *          d.BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator) 创建成功  -- aspectJAdvisorsBuilder
 *      7). 把BeanPostProcessor注册到BeanFactory中：
 *          beanFactory.addBeanPostProcessor(postProcessor)
 *
 *  ------------------------以上是创建 AnnotationAwareAspectJAutoProxyCreator 的过程-----------------------------
 *  AnnotationAwareAspectJAutoProxyCreator ---> InstantiationAwareBeanPostProcessor
 *
 *  4.finishBeanFactoryInitialization(beanFactory); 完成BeanFactory初始化工作，创建剩下的单实例Bean
 *      1).遍历获取容器中的Bean依次创建对象getBean(beanName)
 *          doGetBean - getSingleton
 *      2). 创建bean： AnnotationAwareAspectJAutoProxyCreator会在任何bean创建之前尝试返回bean的实例
 *          [AnnotationAwareAspectJAutoProxyCreator在所有的bean创建之前会有一个拦截, InstantiationAwareBeanPostProcessor]
 *          a.先从缓存中获取当前bean, 如果能获取到说明bean是之前被创建过的，直接使用，否则再创建，只要是创建好的bean都会被缓存起来
 *          b.createBean()创建bean
 *              [BeanPostProcessor是在Bean对象创建完成初始化前后调用]
 *              [InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象]
 *              1.resolveBeforeInstantiation 希望后置处理器在此能返回一个代理对象，如果能返回代理对象就使用，如果不能就继续
 *                  1).后置处理器先尝试返回对象:
 *                      bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 *                          拿到所有后置处理器，如果是 InstantiationAwareBeanPostProcessor 就执行postProcessBeforeInstantiation
 *                      if (bean != null) {
 * 						    bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
 *                      }
 *
 *              2.调用doCreateBean 真正的创建一个Bean实例
 *
 *
 * AnnotationAwareAspectJAutoProxyCreator ---> InstantiationAwareBeanPostProcessor
 *
 *
 * 1.在每一个bean创建之前调用postProcessBeforeInstantiation
 *      关心MathCalculator和LogAspect的创建
 *      1).判断当前bean是否在advisedBeans（保存了所有需要增强的bean）中
 *      2).判断当前bean是否是基础类型的Advice,PointCut,Advisor,AopInfrastructureBean或者是否为切面 this.aspectJAdvisorFactory.isAspect(beanClass)
 *          hasAspectAnnotation(@Aspect)
 *      3). 判断是否需要跳过 shouldSkip(beanClass, beanName)
 *          a.获取候选的增强器（切面里的通知方法） List<Advisor> candidateAdvisors
 *              每个封装的通知方法增强器是InstantiationModelAwarePointCutAdvisor,
 *              判断每个 advisor instanceof AspectJPointcutAdvisor 返回true
 *          b.永远返回false
 *
 *
 * 2.创建对象
 *      postProcessAfterInitialization
 *      return wrapIfNecessary(bean, beanName, cacheKey); // 如果需要的情况下进行包装
 *          a.获取当前bean的所有增强器
 *              Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, null);
 *              找到能在当前bean使用的增强器 findAdvisorsThatCanApply （找到哪些通知方法可以切入到当前的bean）
 *              AopUtils.findAdvisorsThatCanApply(candidateAdvisors, beanClass);
 *              给增强器排序
 *          b.保持当前的bean在advisedBeans中
 *          c.如果bean是需要增强的，就会创建当前bean代理对象
 *              createProxy(beanClass, beanName, specificInterceptors, targetSource);
 *              获取所有的增强器（通知方法） advisors = buildAdvisors(beanName, specificInterceptors);
 *              保存在代理工厂 proxyFactory.addAdvisors(advisors);
 *              proxyFactory.getProxy(getProxyClassLoader());
 *              DefaultAopProxyFactory.createAopProxy(..)
 *
 *              两种形式的代理对象，Spring自动决定创建 1.JDK动态代理还是 2.CGLib代理
 *              if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
 * 				    return new JdkDynamicAopProxy(config);
 *              }
 * 			    return new ObjenesisCglibAopProxy(config);
 * 			d.给容器中返回当前组件使用cglib增强了代理对象
 * 		    e.以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程
 *
 *
 * 3.目标方法执行：
 *      容器中保存了组件的代理对象（cglib增强过后的对象）这个对象里面保存了详细信息（如：目标方法，增强器等）
 *      1). CglibAopProxy.intercept(); 拦截目标方法的执行
 *      2). 根据ProxyFactory对象获取将要执行目标方法的拦截器链
 *          a.List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 *              getInterceptorsAndDynamicInterceptionAdvice
 *          b.遍历所有的增强器，将其转为interceptor: Interceptor[] interceptors = registry.getInterceptors(advisor);
 *          c.将增强器转为我们要用的List<MethodInterceptor>
 *              如果是MethodInterceptor直接加入到集合中
 *              如果不是，则使用AdvisorAdapter将增强器转为MethodInterceptor
 *              转换完成返回MethodInterceptor数组
 *
 *      3). 如果没有拦截器链，直接执行目标方法
 *          拦截器链（每一个通知方法又被包装为方法拦截器，利用MethodInterceptor机制）
 *      4). 如果有拦截器链，把需要执行的目标对象，目标方法，方法参数，拦截器链等信息传入创建一个 CglibMethodInvocation
 *          retVal = new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed();
 *          retVal = processReturnType(proxy, target, method, retVal);
 *      5). 拦截器链的触发过程
 *          a.如果没有拦截器，或者拦截器的索引和拦截器数组-1大小一样（执行到最后一个拦截器） 直接执行目标方法
 *          b.链式获取每一个拦截器：拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回之后再来执行
 *              通过这种拦截器链的形式，保证通知方法与目标方法的执行
 *
 *
 *
 * 总结
 *      1.通过@EnableAspectJAutoProxy开启AOP功能
 *      2.@EnableAspectJAutoProxy注解会给容器中注册一个AnnotationAwareAspectJAutoProxyCreator组件
 *      3.AnnotationAwareAspectJAutoProxyCreator 是一个后置处理器
 *      4.利用容器的创建流程
 *          a.registerBeanPostProcessors()注册后置处理器：创建AnnotationAwareAspectJAutoProxyCreator对象
 *          b.finishBeanFactoryInitialization() 初始化剩下的单实例bean
 *              1.创建业务逻辑组件和切面组件
 *              2.AnnotationAwareAspectJAutoProxyCreator拦截组件创建过程
 *              3.组件创建完成之后，判断组件是否需要增强 wrapIfNecessary
 *                  需要增强：将切面的通知方法，包装成增强器advisor 给业务逻辑组件创建一个代理对象（cglib/JDK dynamic proxy）
 *      5.执行目标方法
 *          a.代理对象执行目标方法
 *          b.CglibAopProxy.intercept(); 拦截目标方法的执行
 *              1).得到目标方法的拦截器链（advisors-->methodInterceptor）
 *              2).利用拦截器的链式机制，依次进入每一个拦截器进行执行
 *              3).效果：前置通知 => 目标方法 => 后置方法 => 返回通知（正常执行）/异常通知（执行产生异常）
 *
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
