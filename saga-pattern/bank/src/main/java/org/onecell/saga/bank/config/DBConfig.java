package org.onecell.saga.bank.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.onecell.saga.bank.property.DatabaseProp;
import org.onecell.saga.bank.property.DbcpProp;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DBConfig {

    @Bean
    @ConfigurationProperties(prefix = "database")
    public DatabaseProp databaseProp()
    {
        return new DatabaseProp();
    }

    @Bean
    @ConfigurationProperties(prefix = "dbcp")
    public DbcpProp dbcpProp()
    {
        return new DbcpProp();
    }



    @Bean(name = "dataSource")
    public DataSource dataSource()
    {
        DatabaseProp databaseProp = databaseProp();
        DbcpProp dbcpProp = dbcpProp();

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(databaseProp.getDriverClassName());
        basicDataSource.setUrl(databaseProp.getUrl());
        basicDataSource.setUsername(databaseProp.getUsername());
        basicDataSource.setPassword(databaseProp.getPassword());
        basicDataSource.setAccessToUnderlyingConnectionAllowed(true);

        basicDataSource.setInitialSize(dbcpProp.getPoolInitialSize());
        basicDataSource.setMaxIdle(20);
        basicDataSource.setMaxTotal(dbcpProp.getPoolMaxSize());
        basicDataSource.setMinIdle(0);
        basicDataSource.setDefaultAutoCommit(true);


        try {
            Connection connection = basicDataSource.getConnection();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return basicDataSource;
    }



    @Bean()
    public SessionFactory sessionFactory() throws IOException {
       String[] packagesScanPath= new String[]{
               "org.axonframework.eventsourcing.eventstore.jpa",
               "org.onecell.saga.bank.aggregates"
       };

        Properties properties = new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto",
                        "update");
                setProperty("hibernate.show_sql",
                        "true");
                setProperty("hibernate.dialect",
                        "org.hibernate.dialect.MySQL57Dialect");
                setProperty("hibernate.jdbc.time_zone",
                        "Asia/Seoul");
                setProperty("format_sql",
                        "true");
                setProperty("show_sql",
                        "true");
                setProperty("use_sql_comments",
                        "true");
            }
        };

        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(
                packagesScanPath);
        sessionFactory.setHibernateProperties(properties);
        try {
            sessionFactory.afterPropertiesSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionFactory.getObject();
    }

    @Primary
    @Bean()
    public HibernateTransactionManager transactionManager() throws IOException {
        HibernateTransactionManager txManager
                = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory());

        txManager.afterPropertiesSet();
        return txManager;
    }


}
