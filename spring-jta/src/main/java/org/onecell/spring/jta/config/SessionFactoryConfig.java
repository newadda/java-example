package org.onecell.spring.jta.config;

import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class SessionFactoryConfig {
    







    public LocalSessionFactoryBean xaLocalSessionFactoryBean(DataSource dataSource) throws IOException {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPackagesToScan(
                defaultMappingClass());


        Properties properties = hibernateDefaultProperties();
        properties.put("hibernate.connection.handling_mode","DELAYED_ACQUISITION_AND_RELEASE_AFTER_STATEMENT");
        bean.setHibernateProperties(properties);
        bean.afterPropertiesSet();
        return bean;

    }

    String [] defaultMappingClass()
    {
        return new String[]{"org.waterworks.lib.db.dto.user"};

    }

    Properties hibernateDefaultProperties() {
        return new Properties() {
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
    }

}
