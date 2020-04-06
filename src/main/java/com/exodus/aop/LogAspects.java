package com.exodus.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect // 告诉Spring这是一个切面类
public class LogAspects {

    // 抽取公共的切入点表达式
    @Pointcut("execution(public int com.exodus.aop.MathCalculator.div(int, int))")
    public void pointCut() {
    }

    // 在目标方法之前切入，括号内容为切入点表达式
//    @Before("public int com.exodus.aop.MathCalculator.div(int, int)")
//    @Before("public int com.exodus.aop.MathCalculator.*(...)")  //表示任意方法，任意参数
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("" + joinPoint.getSignature().getName() + "运行...@Before:参数列表：{" + Arrays.asList(args) + "}");
    }

    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        System.out.println("" + joinPoint.getSignature().getName() + "运行完成...@After ");
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) { // 注意！JoinPoint参数一定要在参数列表的第一位
        System.out.println("" + joinPoint.getSignature().getName() + "运行正常返回...@AfterReturning 结果为：{" + result + "}");
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        System.out.println("除法异常...@AfterThrowing 异常信息：{" + exception + "}");
    }
}
