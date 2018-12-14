package org.onecellboy.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ResourceServerConfigurerAdapter 는 Bearer Auth 이다.
 *
 * Oauth2 에 대한 인증으로 접근제어를 한다.
 *
 * WebSecurityConfigurerAdapter 와 차이점은 WebSecurityConfigurerAdapter은 basic auth이고 oauth2 사용시 인가서버에 대해서만 동작하면 된다.
 *
 * 주의점 : @EnableResourceServer 와 @EnableWebSecurity 이 같은 프로젝트에 둘다 존재한다면 @EnableWebSecurity 은 무시된다.
 *          이 말은 인증서버와 리소스 서버가 같이(같은 프로세스) 동작한다는 말이다. 이를 조심해야한다.
 *
 * 차라리 HttpSecurity 설정을 ResourceServerConfigurerAdapter 에서만 잡아버리는 것도 방법이다.
 * WebSecurityConfigurerAdapter은 없다고 치는 것이다.
 *
 *
 */
/*
 * 주의점 : @EnableResourceServer 와 @EnableWebSecurity 이 같은 프로젝트에 둘다 존재한다면 @EnableWebSecurity 은 무시된다.
 *          이 말은 인증서버와 리소스 서버가 같이(같은 프로세스) 동작한다는 말이다.
 *          이럴 때 HttpSecurity 설정은 ResourceServerConfigurerAdapter 에서 다 하는 것이 좋다.
 *          즉 oauth2 를 사용할 때는 ResourceServerConfigurerAdapter 에서 접근제어 설정
 *          Basic auth(spring security)를 사용 할 때는 WebSecurityConfigurerAdapter 에 접근제어 설정
 *
 *          @EnableResourceServer 는 리소스 서버로 동작하게 해준다. 리소스 서버는 원격지 인가서버에게  restful api를 통해 액세스 토큰을 확인하거나
 *          DB 에 직접 접근하여 액세스 토큰 정보를 가져 올 수 있다.
 *
 *          리소스 서버에서만 필요
 * */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    @Qualifier("tokenStore")
    private TokenStore tokenStore;


    @Autowired
    @Qualifier("localTokenServices")
    ResourceServerTokenServices tokenServices;



    /**
     * 인가서버(Authorization Server) 가 리소스 서버와 분리되어 있을 때 사용하는 방법이다.
     * 원격지에 인가서버가 존재할때..
     * @return
     */

    @Bean(name = "remoteTokenServices")
    public ResourceServerTokenServices remoteTokenServices() {
        final RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl("http://127.0.0.1:80801/oauth/check_token"); // 인증서버 check_token 경로
        tokenServices.setClientId("my_client_id");  // client id 필수
        tokenServices.setClientSecret("my_client_secret"); // client secret 필수

        return tokenServices;
    }

    @Primary
    @Bean(name = "localTokenServices")
    public ResourceServerTokenServices localTokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);

        return defaultTokenServices;
    }


    /**
     * 리소스 서버 설정이다.
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

        // ==== tokenServices 등록 (원격지 인가 서버 혹은 인가서버와 리소스서버가 동일할때)
        // 인가 서버가 원격지에 있을 때 설정이다.
        resources.tokenServices(tokenServices);

        // TODO인증 서버와 리소스 서버가 같이 동작할 때
        //resources.tokenServices(localTokenServices());

        //  ==== 만약 인증서버 한대에 서로 다른 리소스 서버가 있을 경우 Resource ID를 통해 분류가능하다.
        //resources.resourceId()
        resources.authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

            }
        });


        // ===== Exception Handling ====
        // access token 실패시 (resource 접근시 header Authorization 실패시) 여기서 핸들링한다.
        // 또한 /oauth/* 에서 Header Authorization Basic 의 실패도 여기서 받을 수 있다. 이경우 Authorization 헤더가 존재할 때이다.
        //resources.authenticationEntryPoint();

        //  access token 은 존재하나 scope, role 등 권한 위반시
        //  resources.accessDeniedHandler();
        //  ===============================



    }

    /**
     * 자원에 대한 CRUD에 대한 접근권한을 지정할 수 있다.
     * 인증 없이 허용하는 룰을 앞에 인증을 필요로 하는 것을 뒤에 작성하자.
     * 예)
     * http.authorizeRequests().antMatchers("/test").permitAll(); // /test 에 대해 인증 필요없음
     * http.authorizeRequests().anyRequest().authenticated();  // 모든 요청에 대해 인증 필요
     * 이렇게 해주어야 /test 는 인증이 필요없이 요청 가능해 진다. 반대로 작성하면 /test 에대한 허용은 무시된다.
     *
     *
     * @param http
     * @throws Exception
     */

    @Override
    public void configure(HttpSecurity http) throws Exception {

        // 정의 되어 있지 않으면 기본이 허용


        // WebSecurityConfigurerAdapter 의 configure(HttpSecurity http) 코드와 동일하다.
        // 이 코드가 존재하는 이유는 리소스 서버와 인증 서버가 같은 있는 경우
        // 다시 말해 @EnableResourceServer, @EnableWebSecurity 이 같이 있는 경우 @EnableWebSecurity 은 무시된다..

        // oauth2 인증용 설정이다.
        this.oauthConfigure(http);



        /* ===중요=== */
        // 리소스 서버와 인증 서버가 같이 동작시(리소스 서버 설정, 인증 서버 설정 동시 존재)시
        // "/oauth/check_token", "/oauth/token" 등 꼭 인증(header Authorization basic)되어야 하기 때문에 아래와 같은 설정을 해야한다.
        // 이게 존재해야 http basic 인증에 대한 exception 처리를 WebSecurityConfig 에서 핸들링 할 수 있다.
        // 기본 설정이다.


        // 인증이 무조건 필요한 리소스를 등록한다.
        // http.authorizeRequests().antMatchers("/authrequried").authenticated();

        // TODO super.configure 는 기본이 모든 요청에 대한 인증이 필요하게 설정한다.
        // TODO 때문에 이 앞에 인증없이도 허용할 리소스에 대해 정의해야한다.
        //super.configure(http);


        // TODO 이 밑으로 접근제어 하면 된다.
        // 예시 http.authorizeRequests().antMatchers("/authtest1").access("hasRole('USER') and #oauth2.hasScope('write1')");
        /* 예시
        http.authorizeRequests().antMatchers("/").permitAll()
            .antMatchers("/my").authenticated().and().authorizeRequests().anyRequest().hasRole("USER")
            .antMatchers("/my").access("#oauth2.hasScope('read')");
        * */


        // TODO 주석을 풀면 모든 요청이 인증되어야 한다.
        //http.authorizeRequests().antMatchers("/test").permitAll(); // /test 에 대해서는 인증 필요 없음
        //http.authorizeRequests().anyRequest().authenticated(); //모든 요청이 인증되어야 한다.
        http.authorizeRequests().antMatchers("/test**/*").permitAll();


        /* 변경하지마라. 모든 인증요청에 대해서는 인증되어야 한다. */
        http.authorizeRequests().anyRequest().authenticated();


        //http.authorizeRequests().anyRequest().authenticated();


      //  http.authorizeRequests().antMatchers("/authtest2").authenticated().and().formLogin();


       // http.authorizeRequests().antMatchers("/").anonymous();
       // http.authorizeRequests().antMatchers("/authcodetest").permitAll();
       // http.authorizeRequests().antMatchers("/authtest2").denyAll();
      //  http.authorizeRequests().antMatchers("/login").permitAll();
        //http.authorizeRequests().antMatchers("/authtest2").permitAll();
     //   http.authorizeRequests().antMatchers("/login","/oauth/**").permitAll();

        //super.configure(http); // 코드를 보면 어떤 요청이든 인증을 필요로 한다.
         /* 예제
        /// 리소스 서버에서 요청에 대한 접근 권한을 미리 체크한다.
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/**").access("#oauth2.hasScope('read')");
        */
         /*
        http.authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .and().authorizeRequests().and()
                .formLogin().loginPage("/login")
                .permitAll().and().logout().permitAll().and().authorizeRequests()
                .antMatchers("/authcodetest").permitAll().antMatchers("/authtest2")
                .hasAnyRole("ADMIN").anyRequest().authenticated().and();
        */


/*
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/authcodetest").permitAll()
               .and().formLogin()
               .permitAll().and().logout().permitAll();

        http.csrf().disable();
        super.configure(http);*/


/*
        http.
                authorizeRequests()
                .antMatchers("/authtest1")
                .access("hasRole('USER') and #oauth2.hasScope('write1')");


        http.formLogin().loginProcessingUrl("/login")
                .loginPage("/loginForm")
                .permitAll();
*/
/*
        http.authorizeRequests().antMatchers("/authcodetest").permitAll()
            .antMatchers("oauth/authorize").
        ;
        http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/test/test")
                .hasAnyRole("ADMIN").anyRequest().authenticated().and().formLogin()
                .permitAll().and().logout().permitAll();

        http.csrf().disable();
*/

      //  http.formLogin().permitAll();
    //   http.authorizeRequests().antMatchers("/**").permitAll();

     /*

*/
       /*
        http.authorizeRequests()
        .antMatchers("/", "/home").access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
        .and().formLogin().loginPage("/login")
        .usernameParameter("ssoId").passwordParameter("password")
        .and().exceptionHandling().accessDeniedPage("/Access_Denied");
       * */
       /*
        http
                // 강의 특성상 전부 허용으로 작업하겠습니다.
                .authorizeRequests()
                .antMatchers("/**")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/sign-out") // /logout 을 호출할 경우 로그아웃
                .logoutSuccessUrl("/") 	// 로그아웃이 성공했을 경우 이동할 페이지
                .invalidateHttpSession(true)
                .and()
                .formLogin()
                .loginPage("/sign-in") // 로그인 페이지 : 컨트롤러 매핑을 하지 않으면 기본 제공되는 로그인 페이지가 뜬다.
                .loginProcessingUrl("/sign-in/auth")
                .failureUrl("/sign-in?error=exception")
                .defaultSuccessUrl("/")

                .and()
                // 여기 나오는 sso.filter 빈은 다음장에서 작성합니다.
                // 이 장에서 실행을 확인하시려면 당연히 NPE 오류가 나니 아래 소스에 주석을 걸어주시기 바랍니다.
                .addFilterBefore((Filter)context.getBean("sso.filter"), BasicAuthenticationFilter.class);
        */

       /*접근제어 예제*/
        /*
        http
                // Since we want the protected resources to be accessible in the UI as well we need
                // session creation to be allowed (it's disabled by default in 2.0.6)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().antMatchers("/photos/**", "/oauth/users/**", "/oauth/clients/**","/me")
                .and()
                .authorizeRequests()
                .antMatchers("/me").access("#oauth2.hasScope('read')")
                .antMatchers("/photos").access("#oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))")
                .antMatchers("/photos/trusted/**").access("#oauth2.hasScope('trust')")
                .antMatchers("/photos/user/**").access("#oauth2.hasScope('trust')")
                .antMatchers("/photos/**").access("#oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))")
                .regexMatchers(HttpMethod.DELETE, "/oauth/users/([^/].*?)/tokens/.*")
                .access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
                .regexMatchers(HttpMethod.GET, "/oauth/clients/([^/].*?)/users/.*")
                .access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
                .regexMatchers(HttpMethod.GET, "/oauth/clients/.*")
                .access("#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')");
        */
    }




    /**
     * JWT 인증을 위해 존재한다.
     * @return
     */
    /*
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        final Resource resource = new ClassPathResource("public.txt");
        String publicKey = null;
        try {
            publicKey = IOUtils.toString(resource.getInputStream());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        converter.setVerifierKey(publicKey);
        return converter;
    }
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
                 // .antMatchers("/test*").permitAll() // login 페이지 허용, 굳이 필요없는 듯
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



}
