package org.onecellboy.security.config;

import org.onecellboy.security.custom.CustomPasswordEncoder;
import org.onecellboy.security.custom.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.vibur.dbcp.ViburDBCPDataSource;


import javax.sql.DataSource;

@Configuration
public class AturhorizationBeanFactory {

    @Bean(name = "authDataSource")
    public  DataSource dataSource(
            @Value("${oauth2.datasource.url}") String url,
            @Value("${oauth2.database.driverClassName}") String driverClassName,
            @Value("${oauth2.datasource.username}") String userName,
            @Value("${oauth2.datasource.password}") String password
    ) {
        ViburDBCPDataSource dataSource = new ViburDBCPDataSource();

        dataSource.setJdbcUrl(url);
        dataSource.setDriverClassName(driverClassName);
        // dataSource.setUrl(datasourceUrl);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setPoolInitialSize(10);
        dataSource.setPoolMaxSize(100);
        dataSource.setConnectionIdleLimitInSeconds(30);
        dataSource.setLogQueryExecutionLongerThanMs(500);
        dataSource.setLogStackTraceForLongQueryExecution(true);
        dataSource.setStatementCacheMaxSize(200);
        dataSource.start();

        return dataSource;
    }

    @Bean(name = "tokenStore")
    public TokenStore tokenStore(DataSource dataSource) {
        // return new InMemoryTokenStore();

        return new JdbcTokenStore(dataSource);
    }




    @Bean(name = "approvalStore")
    public ApprovalStore approvalStore(DataSource dataSource)
    {
        return new JdbcApprovalStore(dataSource);
    }


    @Bean(name = "authorizationCodeServices")
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource)
    {
        return new JdbcAuthorizationCodeServices(dataSource);
    }


    @Bean("jdbcClientDetailsService")
    public ClientDetailsService jdbcClientDetailsService(DataSource dataSource,PasswordEncoder clientPasswordEncoder) {
        //

        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(clientPasswordEncoder); //DB에 해당 인코딩 방식으로 저장되어 있어야 한다.

        return jdbcClientDetailsService;
    }

    @Bean(name = "authorizationServerTokenServices")
    public AuthorizationServerTokenServices authorizationServerTokenServices(@Autowired @Qualifier("jdbcClientDetailsService") ClientDetailsService clientDetailsService, TokenStore tokenStore)
    {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setClientDetailsService(clientDetailsService);  // 이걸 꼭 적용해야한다. 나의 경우는 토큰 만료시기가 db상 값을 사용하기 위해 적용했다.
        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setSupportRefreshToken(true); // Refresh Token 기능 활성화
        return defaultTokenServices;
    }


    @Bean(name = "clientPasswordEncoder")
    public PasswordEncoder clientPasswordEncoder()
    {
        return new CustomPasswordEncoder();
    }

    @Bean(name = "userDetailsService")
    public UserDetailsService userDetailsService()
    {
        return new CustomUserDetailsService();
    }



}
