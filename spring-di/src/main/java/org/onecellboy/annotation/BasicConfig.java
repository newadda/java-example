package org.onecellboy.annotation;

import org.onecellboy.Basic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("org.onecellboy.annotation.test")
public class BasicConfig {

    @Bean
    @Primary
    public Basic basicByPrimary()
    {
        return new Basic("basic primary");
    }

    @Bean
    public String str()
    { return "test";}



    @Bean
    public Basic basic()
    {
        return new Basic(str());
    }

    @Bean("basic2")
    public Basic basicByName()
    {
        return new Basic("basic2");
    }

    @Bean(value = "basic3")
    @Scope("prototype")
    public Basic basicByScope()
    {
        return new Basic("basic3");
    }


}
