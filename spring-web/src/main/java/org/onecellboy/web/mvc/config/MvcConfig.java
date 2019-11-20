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
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
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

import javax.servlet.ServletException;
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
    public CommonsRequestLoggingFilter logFilter() throws ServletException {
        CommonsRequestLoggingFilter filter
                = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setIncludeClientInfo(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setBeforeMessagePrefix("REQUEST : ");
        filter.setAfterMessagePrefix("RESPONSE : ");
        filter.afterPropertiesSet();
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
     * "classpath:/org/hibernate/validator/ValidationMessages" 은 하이퍼네이트 validationMessages 여서 추가했다.
     *
     * properties 의 key 가 겹칠 경우 먼저 적용된 파일의 key와 value를 사용한다.
     *
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


    /**
     * Spring 의 deafult Validator를 설정한다.
     * 이것을 오버라이드 하지 않으면 javax.validation.Validator를 사용한다.
     * @return
     */
    @Bean
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



    /**
     * Multipart Resolver Config
     * @return
     */
    @Bean
    public CommonsMultipartResolver multipartResolver()
    {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        /* 최대 업로드 가능한 바이트 크기, -1 제한없음, 기본 값 -1*/
        commonsMultipartResolver.setMaxUploadSize(-1);
        /* 디스크에 임시 파일을 생성하기 전에 메모리에 보관할 수 있는 최대 바이트 크기, 기본값은 10240*/
        commonsMultipartResolver.setMaxInMemorySize(10240);
        /* 파싱할 때 사용할 캐릭터 인코딩, 지정하지 않은 경우 httpServletRequest.setEncording()메서드로 지정한 캐릭터 셋 사용, 아무 값도 없을 경우 ISO-//59-1 사용 */
        // commonsMultipartResolver.setDefaultEncoding();

        return commonsMultipartResolver;
    }


}
