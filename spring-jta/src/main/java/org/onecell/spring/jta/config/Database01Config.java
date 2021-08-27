package org.onecell.spring.jta.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.hibernate.SessionFactory;
import org.onecell.spring.jta.lib.datasource.DataSourceBeanFactory;
import org.onecell.spring.jta.lib.property.DatabaseProp;
import org.onecell.spring.jta.lib.sessionfactory.SessionFactoryBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class Database01Config {

    @Bean(name = "databaseProp1")
    @ConfigurationProperties(prefix = "database1")
    public DatabaseProp databaseProp1()
    {
        return new DatabaseProp();
    }

    @Bean(name = "dataSource1")
    public DataSource dataSource1()
    {
        AtomikosDataSourceBean atomikosDataSourceBean = DataSourceBeanFactory.atomikosDataSourceBean("1", databaseProp1());

        return atomikosDataSourceBean;
    }



    @Bean(name = "sessionFactory1")
    public SessionFactory sessionFactory1() throws IOException {
        String[] packagesScanPath= new String[]{"org.onecell.spring.jta.entity"};
        Properties properties = new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto",
                        "none");
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

        SessionFactoryBeanFactory sessionFactoryBeanFactory = new SessionFactoryBeanFactory();
        LocalSessionFactoryBean xaLocalSessionFactoryBean = sessionFactoryBeanFactory.createXaLocalSessionFactoryBean(dataSource1(), packagesScanPath, properties);
        return xaLocalSessionFactoryBean.getObject();
    }


}
