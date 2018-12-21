package org.onecellboy.web;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
   // public static XLogger xLogger = XLoggerFactory.getXLogger(Application.class);
   private static final Logger LOG = LoggerFactory.getLogger(Application.class);



    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
    //    LoggerContext context = (org.apache.logging.log4j.core.LoggerContext)LogManager.getContext(false);
      //  context.setConfigLocation(URI.create("conf/log4j2.xml"));
      //  context.reconfigure();

       // xLogger.debug("Application start");
        LOG.warn("sending hello world response...");

        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        //DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
       // dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

    }
}