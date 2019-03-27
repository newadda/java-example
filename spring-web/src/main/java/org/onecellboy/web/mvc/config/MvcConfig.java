package org.onecellboy.web.mvc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.validation.Validation;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;


/**
 * web의 default 설정은 이곳에서 하면 된다.
 */
@Configuration
@EnableAutoConfiguration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    /**
     * Make sure dates are serialised in
     * ISO-8601 format instead as timestamps
     * Date 포맷을 ISO 8601 포맷으로 직렬화, 역직렬화 하기 위해서 사용
     * 기본은 timestamps 형태로 epoch 타임이다.
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonMessageConverter  = (MappingJackson2HttpMessageConverter) converter;
                ObjectMapper objectMapper  = jsonMessageConverter.getObjectMapper();
                objectMapper.setTimeZone(TimeZone.getDefault());

                objectMapper.disable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
                );
                break;
            }
        }
    }



    /**
     * 빌트인 요청 로깅이다.
     * @return
     */
    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter
                = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setIncludeClientInfo(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }



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

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
       //resolvers.add(0,new RestResponseStatusExceptionResolver());
        //resolvers.add(new RestResponseStatusExceptionResolver());

        Properties p =new Properties();
        p.setProperty("RuntimeException", "test");
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        simpleMappingExceptionResolver.setExceptionMappings(p);
        simpleMappingExceptionResolver.setOrder(1);


      //  resolvers.add(simpleMappingExceptionResolver);
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(0,new RestResponseStatusExceptionResolver());
       //resolvers.add(new RestResponseStatusExceptionResolver());

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

    /**
     * 기본 validator 만들기
     *
     * @return
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource());
        return validatorFactoryBean;
    }

    /**
     * 메세지 소스의 경로를 이것저것 추가하기 위해서 사용
     * "classpath:/ValidationMessages" 는 기본경로이다.
     * @return
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource(){
        ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
        // classpath:/ValidationMessages : 기본 , classpath:/org/onecellboy/common/spring/ValidationMessages : custom
        bundle.setBasenames("classpath:/ValidationMessages","classpath:/org/one/lib/ValidationMessages","classpath:/org/waterworks/lib/ValidationMessages");
        bundle.setFallbackToSystemLocale(true); // 해당 언어에 메세지가 없다면 시스템 기본 locale의 언어 메세지에서 가져온다.
        bundle.setDefaultEncoding("UTF-8");
        return bundle;
    }



    @Override
    public Validator getValidator() {
        return validator();
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Bean
    public LocaleResolver localeResolver(){
        LocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        ((AcceptHeaderLocaleResolver) localeResolver).setDefaultLocale(Locale.KOREA);
        return  localeResolver;
    }

}
