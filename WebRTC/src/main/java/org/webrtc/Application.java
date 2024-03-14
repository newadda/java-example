package org.webrtc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.io.IOException;
import java.util.Locale;
import java.util.TimeZone;


@SpringBootApplication(scanBasePackages = {},exclude = {HibernateJpaAutoConfiguration.class})

//DataSource Auto Configuration 무시하기
//@SpringBootApplication(scanBasePackages = {},exclude = {HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class })
public class Application extends SpringBootServletInitializer implements ApplicationListener<ContextClosedEvent> {
    private static final String TIME_ZONE = "Asia/Seoul";
    private static final String CONFIG_NAME = "application";

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {

    }

    //// war 로 외부 Tomcat 에서 실행 될때 사용할 수 있다.
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

        /// 기본적으로 읽는 properties 파일 이름을 프로젝트명등으로 바꿀수 있다.
        /// 예를 들어 기본은 application.properties 이지만 app-test.properteis 로 바꿀수 있다.
        //return builder.sources(WebApplication.class).properties("spring.config.name: "+ CONFIG_NAME);

        return builder.sources(Application.class).properties("spring.config.name: "+CONFIG_NAME);
        //return super.configure(builder);
    }

    public static void main(String[] args) throws IOException {
        /// 내장 톰캣 사용시 기본적으로 읽는 properties 파일 이름을 프로젝트명등으로 바꿀수 있다.
        System.setProperty("spring.config.name", CONFIG_NAME);

        System.setProperty("file.encoding", "UTF-8");



        // 디폴트 타임존 설정
        TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));

        // 디폴트 locale 설정
        Locale.setDefault(new Locale("ko","kr"));

        SpringApplication.run(Application.class, args);


    }

}
