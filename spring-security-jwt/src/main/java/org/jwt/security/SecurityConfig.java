package org.jwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)  // prePostEnabled:@PreAuthorize, @PostAuthorize 어노테이션을 사용가능하게 한다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inMemoryUserDetailsManager());

        // DB 베이스
        //auth.userDetailsService(dbUserDeailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http = http.cors().and().csrf().disable(); // cors() 는 preflight 요청은 인증처리 하지 않겠다는 의미이다.

        http =  http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and(); //JWT를 사용할 것이므로 session 으로 상태를 저장할 필요없음

        /*http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest) /// CORS preflight 요청은 인증처리 하지 않겠다는 의미이다. cors()와 같은 의미라 주석처리.
                .permitAll()*/

        /// 접근제어할 부분
        http.authorizeRequests().antMatchers("/**/login").permitAll()
                .antMatchers("/**/test").permitAll()
                // login url은 권한이 필요없다.
                .anyRequest().authenticated();

        //// UsernamePasswordAuthenticationFilter 자기전에 할 filter를 지정한다.
        /// jwt 처리
        http.addFilterBefore(
                jwtTokenFilterBean(),
                //jwtAuthenticationFilterBean(),
                UsernamePasswordAuthenticationFilter.class
        );
    }

    @Bean
    public JwtTokenUtil jwtTokenUtilBean()
    {
        return new JwtTokenUtil();
    }

    /// 주석 처리 안하면 자동으로 injection 된다.
    //@Bean
    public JwtAuthenticationFilter jwtAuthenticationFilterBean() throws Exception {

        AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/jwt_login");

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(DEFAULT_ANT_PATH_REQUEST_MATCHER, jwtTokenUtilBean(), inMemoryUserDetailsManager());
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return jwtAuthenticationFilter;
    }


    @Bean
    public JwtTokenFilter jwtTokenFilterBean() {
        return new JwtTokenFilter(jwtTokenUtilBean(),inMemoryUserDetailsManager());
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager()
    {
        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(User.withUsername("test").password(passwordEncoder().encode("test"))
                .roles("ADMIN1", "USER").build());

        return new InMemoryUserDetailsManager(userDetailsList);
    }


    /// DB 베이스에서 쓰임
    /*
    @Bean
    public UserDetailsService  dbUserDeailsService()
    {
        return new CustomUserDetailsService(tbInfoUserRepo);
    }
    */



    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


    // Used by spring security if CORS is enabled.
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        //config.addAllowedOrigin("*"); // allowCredentials 가 true 이면 allowedOrigin 은 "*" 를 허용하지 않아 주석처리
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
