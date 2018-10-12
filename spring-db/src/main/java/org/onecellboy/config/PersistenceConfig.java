package org.onecellboy.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.vibur.dbcp.ViburDBCPDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:db.properties" })
public class PersistenceConfig {

    @Autowired
    private Environment env;

    @Bean(name = "viburDataSource")
    public DataSource viburDataSource(
            @Value("${datasource.url}") String datasourceUrl,
            @Value("${database.driverClassName}") String dbDriverClassName,
            @Value("${datasource.username}") String dbUsername,
            @Value("${datasource.password}") String dbPassword
    ) {


        ViburDBCPDataSource dataSource = new ViburDBCPDataSource();

        dataSource.setJdbcUrl(datasourceUrl);
        dataSource.setDriverClassName(dbDriverClassName);
        // dataSource.setUrl(datasourceUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        dataSource.setPoolInitialSize(10);
        dataSource.setPoolMaxSize(100);

        dataSource.setConnectionIdleLimitInSeconds(30);


        dataSource.setLogQueryExecutionLongerThanMs(500);
        dataSource.setLogStackTraceForLongQueryExecution(true);

        dataSource.setStatementCacheMaxSize(200);

        dataSource.start();

        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(@Autowired @Qualifier("viburDataSource") DataSource datasource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(datasource);
        sessionFactory.setPackagesToScan(
                new String[] { "org.onecellboy.model" });
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(
            SessionFactory sessionFactory) {

        HibernateTransactionManager txManager
                = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }

    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto",
                        env.getProperty("hibernate.hbm2ddl.auto"));
                setProperty("hibernate.dialect",
                        env.getProperty("hibernate.dialect"));
                setProperty("hibernate.globally_quoted_identifiers",
                        "true");
            }
        };
    }

}
