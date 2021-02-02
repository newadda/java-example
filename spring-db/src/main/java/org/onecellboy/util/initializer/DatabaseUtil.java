package org.onecellboy.util.initializer;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class DatabaseUtil {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseUtil.class);
    public static DataSource createDataSource(DatabaseProp databaseProp, DbcpProp dbcpProp)
    {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(databaseProp.getDriverClassName());
        basicDataSource.setUrl(databaseProp.getUrl());
        basicDataSource.setUsername(databaseProp.getUsername());
        basicDataSource.setPassword(databaseProp.getPassword());
        basicDataSource.setAccessToUnderlyingConnectionAllowed(true);
        basicDataSource.setDefaultSchema(databaseProp.getDefaultSchema());
        basicDataSource.setInitialSize(dbcpProp.getPoolInitialSize());
        basicDataSource.setMaxIdle(20);
        basicDataSource.setMaxTotal(dbcpProp.getPoolMaxSize());
        basicDataSource.setMinIdle(0);
        basicDataSource.setDefaultAutoCommit(true);

        return basicDataSource;
    }

    static final String[] packagesScanPaths = new String[]{
            "org.xitech.collector.lib.repo.entity"
    };

    public static EntityManagerFactory createEntityManagerFactory(DataSource dataSource, Properties properties)
    {
        return  createEntityManagerFactory(dataSource,packagesScanPaths,properties);
    }

    public static EntityManagerFactory createEntityManagerFactory(DataSource dataSource, String[] packagesScanPaths, Properties properties)
    {

        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(
                packagesScanPaths);
        sessionFactory.setHibernateProperties(properties);
        try {
            sessionFactory.afterPropertiesSet();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sessionFactory.getObject();
    }


    public  static PlatformTransactionManager createTransactionManager(EntityManagerFactory entityManagerFactory)
    {

        HibernateTransactionManager txManager
                = new HibernateTransactionManager();
        txManager.setSessionFactory((SessionFactory) entityManagerFactory);

        txManager.afterPropertiesSet();
        return txManager;

    }

    public static Properties hibernateDefaultProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto",
                        "none");
                setProperty("hibernate.show_sql",
                        "true");
                setProperty("hibernate.jdbc.time_zone",
                        "Asia/Seoul");
                setProperty("show_sql",
                        "false");
                setProperty("format_sql",
                        "false");
                setProperty("use_sql_commnets",
                        "true");
            }
        };
    }


}
