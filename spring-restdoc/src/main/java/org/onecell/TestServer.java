package org.onecell;

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


@SpringBootApplication(scanBasePackages = {}, exclude = {HibernateJpaAutoConfiguration.class})
public class TestServer extends SpringBootServletInitializer implements ApplicationListener<ContextClosedEvent> {

    private static final String TIME_ZONE = "Asia/Seoul";
    private static final String LOG_CONFIG_PATH = "./config/log4j2.xml";
    private static final String CONFIG_NAME = "istech";

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TestServer.class).properties("spring.config.name: " + CONFIG_NAME);
        // return builder.sources(WebApplication.class);
    }

    public static void main(String[] args) throws IOException {
        System.setProperty("spring.config.name", CONFIG_NAME);
        System.setProperty("file.encoding", "UTF-8");

        // 디폴트 타임존 설정
        TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));

        // 디폴트 locale 설정
        Locale.setDefault(new Locale("ko", "kr"));

        // initLog();
        SpringApplication.run(TestServer.class, args);


    }




}