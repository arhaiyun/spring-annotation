package com.exodus.config;

import com.exodus.bean.Yellow;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Profile:
 * Spring为我们提供的可以根据当前环境，动态的激活和切换一系列组件（bean）的功能
 * <p>
 * 开发环境、测试环境、生产环境
 * 数据源：(/A) (/B) (/C)
 *
 * @Profile 指定组件在哪个环境的情况下才能被注册到容器中，不指定，任何环境下都能注册这个组件
 *
 * 1.通过@Profile加了环境标识的bean, 只有这个环境被激活的时候才能注册到容器当中
 * 2.@Profile写在配置类上，只有是在指定的环境下，整个配置类的所有配置才能生效
 * 3.没有标注环境标识的bean在任何环境下都是可以加载的
 *
 */
//@Profile("test")
@PropertySource(value = {"classpath:config/dbconfig.properties"})
@Configuration
public class MainConfigOfProfile implements EmbeddedValueResolverAware {

    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String password;

    private StringValueResolver valueResolver;
    private String driverClass;

    @Profile("test")
    @Bean
    public Yellow yellow() {
        return new Yellow();
    }

//    @Profile(("default"))
    @Profile(("test"))
    @Bean("testDataSource")
    public DataSource dataSourceTest() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword("bitren");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");

        return dataSource;
    }

    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDev(@Value("${db.password}") String pwd) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("bitren");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/ssm_crud");
        dataSource.setDriverClass(driverClass);

        return dataSource;
    }

    @Profile("prod")
    @Bean("prodDataSource")
    public DataSource dataSourceProd() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("bitren");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/prod_demo");
        String driverClass = valueResolver.resolveStringValue("${db.driverClass}");
        dataSource.setDriverClass(driverClass);

        return dataSource;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        valueResolver = resolver;
        driverClass = valueResolver.resolveStringValue("${db.driverClass}");
    }
}
