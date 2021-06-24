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

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http = http.cors().and().csrf().disable();

        http =  http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and(); //JWT를 사용할 것이므로 session 으로 상태를 저장할 필요없음

        /// 접근제어할 부분
        http.authorizeRequests().antMatchers("/**/login").permitAll()
                .antMatchers("/**/test").permitAll()
                // login url은 권한이 필요없다.
                .anyRequest().authenticated();

        /// jwt 처리
        http.addFilterBefore(
                jwtTokenFilterBean(),
                UsernamePasswordAuthenticationFilter.class
        );
    }

    @Bean
    public JwtTokenUtil jwtTokenUtilBean()
    {
        return new JwtTokenUtil();
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
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
