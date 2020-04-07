package com.exodus.tx;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * 声明式事务：
 * <p>
 * 环境搭建：
 *      1.导入相关依赖
 *          数据源、数据库驱动、Spring-jdbc模块
 *      2.配置数据源、JDBCTemplate(Spring提供的简化数据操作的工具)操作数据
 *      3.给方法上标注 @Transactional表示当前方法为一个事务方法 （UserService.java）
 *      4.配置类添加 @EnableTransactionManagement 开启基于注释的事务管理功能
 *      5.配置事务管理器来管理事务
 *
 *  Spring Transaction 原理:
 *      1.利用@EnableTransactionManagement给容器中注册组件TransactionManagementConfigurationSelector
 *          导入两个组件
 *          AutoProxyRegistrar
 *          ProxyTransactionManagementConfiguration
 *
 *      2.AutoProxyRegistrar：
 *          给容器中注册 InfrastructureAdvisorAutoProxyCreator
 *          利用后置处理器机制在对象创建以后包装对象，返回一个代理对象（增强器），代理对象执行方法利用拦截器链执行
 *
 *      3.ProxyTransactionManagementConfiguration 做了什么？
 *          给容器中注册事务增强器，
 *          a.事务增强器用事务注解的信息，AnnotationTransactionAttributeSource解析事务注解
 *          b.事务拦截器
 *              transactionInterceptor 保存了事务属性信息，事务管理器
 *              他是一个MethodInterceptor
 *              在目标方法执行的时候执行拦截器链
 *              1).先获取事务相关的属性 TransactionAttribute
 *              2).再获取 PlatformTransactionManager，如果事先没有添加指定任何TransactionManager
 *                  最终会从容器中获取一个PlatformTransactionManager
 *              3).执行目标方法
 *                  如果异常，获取到事务管理器，利用事务管理器回滚这次操作
 *                  如果正常：利用事务管理器提交事务
 *
 */
@EnableTransactionManagement
@ComponentScan("com.exodus.tx")
@Configuration
public class TxConfig {

    // 数据源
    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("bitren");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
//        dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
//        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/exodus");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/exodus?3useUnicode=true&characterEncoding=utf8");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
        // Spring对@Configuration会有特殊处理，给容器中添加组件的方法，多次调用只是从容器中找到相应的组件
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    // 一定要将事务管理器注册在容器中
    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource());
    }
}
