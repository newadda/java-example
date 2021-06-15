package org.onecell.spring.template;


import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.net.URI;
import java.util.Locale;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication(scanBasePackages = {})
@EnableAsync
public class WebApplication extends SpringBootServletInitializer implements ApplicationListener<ContextClosedEvent> {

    private static final String TIME_ZONE = "Asia/Seoul";
    private static final String LOG_CONFIG_PATH = "./config/log4j2.xml";

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {

    }

    public static void main(String[] args) throws IOException {
        System.setProperty("file.encoding", "UTF-8");

        // 디폴트 타임존 설정
        TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));

        // 디폴트 locale 설정
        Locale.setDefault(new Locale("ko","kr"));

        initLog();


        SpringApplication.run(WebApplication.class, args);


    }


    public static void initLog()
    {
        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(true);
        context.setConfigLocation(URI.create(LOG_CONFIG_PATH));
        context.reconfigure();

        log.error("zzzzzzzzzzz");
        log.info("zzzzzzzzzzz");


    }




}
