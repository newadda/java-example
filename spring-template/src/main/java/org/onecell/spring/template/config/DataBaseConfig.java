package org.onecell.spring.template.config;


import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.istech.libs.db.SessionFactoryBeanFactory;
import org.istech.libs.db.TransactionManagerFactory;
import org.istech.properties.DatabaseProp;
import org.istech.properties.DbcpProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DataBaseConfig {
    @Autowired
    PropertyConfig propertyConfig;

    static final String[] packagesScanPaths = new String[]{
            "db.entity.test"
    };
    @Primary
    @Bean(name = "Datasource")
    public DataSource dataSource()
    {
        DatabaseProp databaseProp = propertyConfig.databaseProp();
        DbcpProp dbcpProp = propertyConfig.dbcpProp();

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

    @Primary
    @Bean(name = "EntityManagerFactory")
    public SessionFactory sessionFactory(@Qualifier("Datasource")DataSource viburDatasource) throws IOException {
        Properties properties = propertyConfig.hibernateProperties();
        SessionFactoryBeanFactory sessionFactoryBeanFactory = new SessionFactoryBeanFactory();


        LocalSessionFactoryBean hibernateLocalSessionFactoryBean = sessionFactoryBeanFactory.createHibernateLocalSessionFactoryBean(viburDatasource, packagesScanPaths, properties);
        hibernateLocalSessionFactoryBean.afterPropertiesSet();

        SessionFactory sessionFactory = hibernateLocalSessionFactoryBean.getObject();
        return sessionFactory;
    }

    @Primary
    @Bean(name = "TransactionManager")
    public HibernateTransactionManager transactionManager(@Qualifier("EntityManagerFactory") SessionFactory sessionFactory)
    {
        TransactionManagerFactory transactionManagerFactory = new TransactionManagerFactory();
        HibernateTransactionManager hibernateTransactionManger = transactionManagerFactory.createHibernateTransactionManger(sessionFactory);



        return  hibernateTransactionManger;
    }









}
