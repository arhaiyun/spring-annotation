package com.exodus.tx;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
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
