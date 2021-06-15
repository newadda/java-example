package org.onecell.spring.template.config;


import org.onecell.spring.template.properties.DatabaseProp;
import org.onecell.spring.template.properties.DbcpProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Properties;

@Configuration
public class PropertyConfig {
    @Autowired
    private Environment env;

    @Bean
    @ConfigurationProperties(prefix = "database")
    public DatabaseProp databaseProp()
    {
        return new DatabaseProp();
    }

    @Bean
    @ConfigurationProperties("dbcp")
    public DbcpProp dbcpProp()
    {
        return new DbcpProp();
    }

    @Bean
    @ConfigurationProperties("jpa")
    public Properties hibernateProperties()
    {
        Properties properties = new Properties();
        return properties;
    }
}
