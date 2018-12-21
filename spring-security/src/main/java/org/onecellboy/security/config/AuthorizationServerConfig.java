package org.onecellboy.security.config;

import org.onecellboy.security.custom.CustomOAuth2RequestValidator;
import org.onecellboy.security.custom.CustomOauthExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * AuthorizationServerConfigurerAdaper 는 Oauth2 의 클라이언트 인증을 담당한다.
 * 이때 client가 아닌 user(Resource Owner) 인증은 기존의 spring.security 를 담당하는 WebSecurityConfigurerAdapter 를 사용한다.
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authDataSource")
    private DataSource dataSource;

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("authenticationManager")
    AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("jdbcClientDetailsService")
    ClientDetailsService clientDetailsService;

    @Autowired
    @Qualifier("authorizationServerTokenServices")
    AuthorizationServerTokenServices authorizationServerTokenServices;


    @Autowired
    @Qualifier("authorizationCodeServices")
    AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    @Qualifier("approvalStore")
    ApprovalStore approvalStore;



    /**
     * 인증서버 자체의 보안 정볼르 설정하는 부분이다.
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()"); // 인증된 사용자만이 /oauth/check_token 가능

    }

    /**
     * Client 에 대한 정보를 설정하는 부분이다.
     * InMemory 기반이나 JDBC 구현체를 정의하는데 사용할 수 있다.
     *
     * 클라이언트의 중요한 속성은 다음과 같다.
     * clientId: (필수) 클라이언트 id.
     * secret: (신뢰하는 클라이언트(trusted clients)일 때는 필수) 클라이언트 시크릿, 만약 있다면.
     * scope: 클라이언트를 제한하는 범위. 범위를 지정하지 않거나 빈 값이라면 (기본으로) 이 클라이언트는 범위를 제한받지 않음.
     * authorizedGrantTypes: 사용하고자 하는 클라이언트가 인가받은 허가 유형. 기본값은 빈값이다.
     * authorities: 클라이언트가 허가 받은 인가 (보통 Spring Security 인가).
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /* inMemory 로 구현*/
      /*  clients.inMemory()
                .withClient("sampleClientId")
                .authorizedGrantTypes("implicit")
                .scopes("read")
                .autoApprove(true)
                .and()
                .withClient("clientIdPassword")
                .secret("secret")
                .authorizedGrantTypes(
                        "password","authorization_code", "refresh_token")
                .scopes("read");
        */


        /* clientDetailsService 직접 구현*/
        clients.withClientDetails(clientDetailsService);

    }

    /**
     * Oauth2 서버가 작동하기 위한 Endpoint에 대한 정보를 설정하는 부분이다.
     * @param endpoints
     * @throws Exception
     */

    /**
     * Grant Types 중 password를 제외하고는 모든 허가 유형을 제공한다.
     * authenticationManager: password 허가로 전환하기 위해서는 AuthenticationManager를 주입해야 한다
     * userDetailsService: UserDetailsService를 주입하거나 어떤 방법으로든 글로벌하게 구성할 수 있다면 (예를 들어, GlobalAuthenticationManagerConfigurer), refresh token 허가는 계정이 여전히 활성화되어 있는지 보장하기 위해 user details에서 검증을 포함하게 된다.
     * authorizationCodeServices: auth code 허가를 위해 인가 코드 서비스(AuthorizationCodeServices의 인스턴스)를 정의한다.
     * implicitGrantService: implicit 허가 동안에 상태를 관리한다.
     * tokenGranter: TokenGranter (허가 제어 전체를 포함하며 위의 다른 속성은 무시한다)
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {


        endpoints
                .requestValidator(new CustomOAuth2RequestValidator())
                .tokenServices(authorizationServerTokenServices)
                .authorizationCodeServices(authorizationCodeServices)
                .approvalStore(approvalStore)
                .authenticationManager(authenticationManager) // grant_type : password 를 위해서는 AuthenticationManager를 등록해야 한다.
                .userDetailsService(userDetailsService) // refresh_token 을 하기 위해서는 UserDetailsService를 꼭 등록해야한다.

         // oauth2 exception 기본 처리 핸들링 법
        .exceptionTranslator(new CustomOauthExceptionTranslator()
            );

            /* // oauth2 관련 exception 처리 핸들링 법 , 일단 기본 클라이언트인증 성공 (header Authorization Basic 은 성공한 상태일때) 후 token이나 권한 실패일때
        // 다시 말하지만 /oauth/* 에 대한 header Authorization Basic 인증실패(기본인증)에 대한 핸들링은 아니다.
        .exceptionTranslator(exception -> {
            if (exception instanceof OAuth2Exception) {
                OAuth2Exception oAuth2Exception = (OAuth2Exception) exception;
                return ResponseEntity
                        .status(oAuth2Exception.getHttpErrorCode())
                        .body(new CustomOauthException(oAuth2Exception.getMessage()));
            } else {
                throw exception;
            }
        });
        */



    }

}
