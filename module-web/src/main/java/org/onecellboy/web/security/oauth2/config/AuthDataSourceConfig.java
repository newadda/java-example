package org.onecellboy.web.security.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.vibur.dbcp.ViburDBCPDataSource;
import org.vibur.dbcp.ViburDBCPObjectFactory;
import org.vibur.dbcp.ViburDataSource;

import javax.sql.DataSource;

@Configuration
public class AuthDataSourceConfig {

    @Bean(name = "DBDataSource")
    public  DataSource DBDataSource(
            @Value("${oauth2.datasource.url}") String datasourceUrl,
            @Value("${oauth2.database.driverClassName}") String dbDriverClassName,
            @Value("${oauth2.datasource.username}") String dbUsername,
            @Value("${oauth2.datasource.password}") String dbPassword
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






/*
    @Bean
    public UserDetailsService userDetailsService(@Qualifier("DBDataSource") DataSource dataSource) {
        return new JdbcUserDetailsManager();

    }
    */


}
