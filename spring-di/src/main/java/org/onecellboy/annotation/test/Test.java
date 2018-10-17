package org.onecellboy.annotation.test;

import org.onecellboy.Basic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class Test {
    @Bean
    @Primary
    public Basic cByPrimary()
    {
        return new Basic("basic primary1");
    }
}
