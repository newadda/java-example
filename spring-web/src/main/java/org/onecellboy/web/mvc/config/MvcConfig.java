package org.onecellboy.web.mvc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


/**
 * web의 default 설정은 이곳에서 하면 된다.
 */
@Configuration
@EnableAutoConfiguration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {



    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
       // registry.addResourceHandler("/*.js/**").addResourceLocations("/ui/static/");
       // registry.addResourceHandler("/*.css/**").addResourceLocations("/ui/static/");
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/").setViewName("login");
        //registry.addViewController("/login").setViewName("login");
    }


    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
      //  resolver.setViewClass (JstlView.class);
       // resolver.setViewNames("*");
        return resolver;
    }

   @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(getBundle());
        return validatorFactoryBean;
    }


    public ReloadableResourceBundleMessageSource getBundle(){
        ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
        bundle.setBasenames("classpath:/ValidationMessages","classpath:/org/onecellboy/common/spring/ValidationMessages");
        return bundle;
    }


    @Override
    public Validator getValidator() {
        return validator();
    }


}
