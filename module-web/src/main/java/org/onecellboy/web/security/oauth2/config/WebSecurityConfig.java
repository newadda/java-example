package org.onecellboy.web.security.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


/**
 * Basic Auth
 *
 * WebSecurityConfigurerAdapter 은 Spring.Security 설정을 담당한다. spring.security 는 Basic 인증(기본적인 login)을 통한 사용자 인증과 권한 인증을 말한다.
 *
 * 기본적인 http basic 인증이다. Custom 한 인증을 자유롭게 구현 가능하다.
 *
 * Oauth2에서도 Resource Owner(사용자) 인증은 이것을 사용한다. 재사용이며 Oauth2 또한 Spring.Security를 확장 구현했다.
 *
 * spring.security 에서는 roles 을 통해 사용자의 권한을 확인한다.
 *
 * role  과 authority 은 동일한 것이다.
 *
 * 예를 들어 role 이 ADMIN 일때 같은 값은 authority 은 ROLE_ADMIN 이다.
 *
 * roles("USER")와 authorities("ROLE_USER") 는 같은 것이다. roles 은 'ROLE_'의 접미사를 표현하지 않는다.
 *
 */
/*
 * 주의점 : @EnableResourceServer 와 @EnableWebSecurity 이 같은 프로젝트에 둘다 존재한다면 @EnableWebSecurity 은 무시된다.
 *          이 말은 인증서버와 리소스 서버가 같이(같은 프로세스) 동작한다는 말이다.
 *          이럴 때 HttpSecurity 설정은 ResourceServerConfigurerAdapter 에서 다 하는 것이 좋다.
 *          즉 oauth2 를 사용할 때는 ResourceServerConfigurerAdapter 에서 접근제어 설정
 *          Basic auth(spring security)를 사용 할 때는 WebSecurityConfigurerAdapter 에 접근제어 설정
* */
@Configuration
@EnableWebSecurity
//@Order(value = 1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     *
     * spring oauth2 의 사용자(user) 인증은 spring security를 사용한다.
     *
     * 따라서 spring security 의 AuthenticationManager 를 반환하여 AuthorizationServerConfigurerAdapter 구현체에서 사용한다.
     *
     * @return
     * @throws Exception
     */
    @Override
    @Bean(name = "authenticationManager")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 인증 서비스 등록하는 부분이다.
     * 인증 서비스는 실제로 사용자 정보를 가져오는 구현이다.
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /* 메모리 기반 계정 일 때*/
        auth.inMemoryAuthentication()
                .withUser("shh").password(new CustomPasswordEncoder().encode("shh")).roles("USER");
                 //.and().passwordEncoder(); // 패스워드 인코딩법



        /* 커스텀 UserDetailsService
        * UserDetailsService 를 구현하여 사용해야 한다.
        * */
        /*
        auth.userDetailsService(new CustomUserDetailsService()).passwordEncoder(new CustomPasswordEncoder());
         */

    }

    /**
     *
     *  여기서 ignoring() 처리하면 configure(HttpSecurity http) 의 접근제어가 무시된다.
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {

        /// 리소스 서버와 한 프로세스로 동작하는 경우 리소스 경로를 넣어 ResourceServerConfigurerAdapter 에서
        /// 처리되도록해야한다.
        web.ignoring().antMatchers("/webapp/**","/resources/**"
               ,"/webjars/**", "/images/**", "/oauth/uncache_approvals", "/oauth/cache_approvals");


        //web.ignoring().antMatchers("/authcodetest","/authtest2");

       // super.configure(web);
    }




    /**
     * 인터셉터로 요청을 안전하게 보호하는 방법을 설정하기 위한 오버라이딩이다.
     *
     * 먼저 적용된 접근제어를 따른다
     * 예를 들어
     * http.authorizeRequests()
     * 			.antMatchers("/**").permitAll() // 모든 경로 접근 허용
     * 			.antMatchers("/mypage").authenticated(); // /mypage 인증된 사용자만 접근 허용
     * 일때 /mypage 로 접근하면 인증없이 접근이 잘 된다. 왜냐면 antMatchers("/**").permitAll() 가 먼저 선언되었기 때문이다.
     *
     * 쉽게 생각해서 접근제어에 매칭이 되면 그 다음 접근제어는 무시된다는 것이다.
     *
     * Spring.Oauth2 에서의 ResourceServerConfigurerAdapter 에서의 접근제어와 차이가 무엇이냐면
     *
     * ResourceServerConfigurerAdapter 에서의 접근제어는 oauth2 기반 접근제어이고
     *
     * WebSecurityConfigurerAdapter 에서의 접근제어는 basic auth 기반 접근제어이다.
     *
     * 이는 인증 서버와 리소스 서버가 분리된 개념이고 실제로 구성로 분리 시켜 구성할 수 있기 때문이다.
     *
     * 인증 서버의 접근제어이다.
     * oauth/token
     * oauth/authorize
     * oauth/check_token
     * 등 인증에 관련된 접근제어이다.
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ////  정의하지 않으면 기본 접근 제어는 다 허용이다. 하나씩 막는 개념이다.

        /// oauth2 인증 설정
        this.oauthConfigure(http);

    }

    /**
     *   oauth2 인증 설정
     */
    public  void oauthConfigure(HttpSecurity http) throws Exception
    {
        //// /oauth/authorize 의 경우는 꼭 로그인(oauth 로그인이 아니다. 일반 사용자 로그인이다.)해야한다.
        ////  /oauth/authorize 의 response_type=code 즉 Authorization Code-Grant Type 을 보면 로그인이 왜 필요한 지 알게 될 것이다.

        http.sessionManagement().sessionFixation().changeSessionId()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                .authorizeRequests()
                // 이부분이 가장 중요하다.
                // oauth2 grant type - authorization code 사용시 /oauth/authorize 요청하면 /login 으로 리다이렉션하여
                // 사용자 인증하고 다시 /oauth/authorize 다시 리다이렉션하여 code를 받게 된다.
                .antMatchers("/oauth/authorize").authenticated()
                //  .antMatchers("/login").permitAll() // login 페이지 허용, 굳이 필요없는 듯
                //.anyRequest().hasRole("USER") // 그외는 ROLE_USER 필요
                //.and()
                //.exceptionHandling()
                //.accessDeniedHandler() // 예외처리 루틴
                //.accessDeniedPage("/login.jsp?authorization_error=true") // 예외처리시 페이지
                .and()
                // TODO: put CSRF protection back into this endpoint
                .csrf()
                //.requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize")) // CSRF 지정 패턴
                .disable() //CSRF 끄기
                .logout()
                //.logoutUrl("/logout") // 로그아웃 URL
                //.logoutSuccessUrl("/login.jsp") // 로그아웃시 리다이렉트 URL
                .and()
                //.loginProcessingUrl("/login") // POST 로 넘어오는 로그인 처리 URL
                //.loginPage("/login") //로그인 페이지, 따로 만들지 않았다면 주석 처리해야한다.
                //.failureUrl("/error") // 로그인 실패시 URL
                .formLogin() //로그인
                .permitAll();  // 허용

    }





/*
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        if (null != cookieSettings.getName()) { serializer.setCookieName(cookieSettings.getName()); }
        if (null != cookieSettings.getPath()) { serializer.setCookiePath(cookieSettings.getPath()); }
        if (null != cookieSettings.getHttpOnly()) { serializer.setUseHttpOnlyCookie(cookieSettings.getHttpOnly()); }
        if (null != cookieSettings.getMaxAge()) { serializer.setCookieMaxAge(cookieSettings.getMaxAge()); }
        if (null != cookieSettings.getSecure()) { serializer.setUseSecureCookie(cookieSettings.getSecure()); }
        if (null != cookieSettings.getDomain()) { serializer.setDomainName(cookieSettings.getDomain()); }
        return serializer;
    }
*/





}
