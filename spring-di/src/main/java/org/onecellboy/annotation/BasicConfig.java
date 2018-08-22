package org.onecellboy.annotation;

import org.onecellboy.Basic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

@Configuration
public class BasicConfig {

    @Bean
    @Primary
    public Basic basicByPrimary()
    {
        return new Basic("basic primary");
    }


    @Bean
    public Basic basic()
    {
        return new Basic("basic");
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
