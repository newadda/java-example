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

        dataSource.setDefaultAutoCommit(false); // autocommit을 false로 해야 트랜잭션 문제시 rollback 된다.

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

    /**
     * =hibernate.hbm2ddl.auto =
     * none : 기본 값이며 아무 일도 일어나지 않는다.
     * create-only : 데이터베이스를 새로 생성한다.
     * drop : 데이터베이스를 drop 한다.
     * create : 데이터베이스를 drop 한 후, 데이터베이스를 새로 생성한다.(기능적으로는 drop + create-only와 같다)
     * create-drop : SessionFactory가 시작될 때 스키마를 drop하고 재생성하며, SessionFactory가 종료될 때도 스키마를 drop 한다.
     * validate : 데이터베이스 스키마를 검증 한다.
     * update : 데이터베이스 스키마를 갱신 한다.
     *
     *
     * @return
     */
    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto",
                        "none");
                setProperty("hibernate.dialect",
                        env.getProperty("hibernate.dialect"));
                setProperty("hibernate.globally_quoted_identifiers",
                        "true");
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
