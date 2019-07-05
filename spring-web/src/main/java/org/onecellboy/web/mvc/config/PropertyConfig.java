package org.onecellboy.web.mvc.config;

import org.onecellboy.properties.DatabaseProp;
import org.onecellboy.properties.StorageProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Properties;

@Configuration
public class PropertyConfig {

    /**
     * 환경변수
     */
    @Autowired
    private Environment env;

    /**
     *  test.database 로 시작하는 설정을 변수로 바인딩한다.
     * */
    @Bean
    @ConfigurationProperties(prefix = "test.database")
    public DatabaseProp databaseProp()
    {
        return new DatabaseProp();
    }
    /**
     *  test.storage 로 시작하는 설정을 변수로 바인딩한다.
     * */
    @Bean
    @ConfigurationProperties(prefix = "test.storage")
    public StorageProp storageProp()
    {
        return new StorageProp();
    }


    /**
     *  jpa 로 시작하는 설정을 Properties 객체로 변환한다.
     *  이때 jpa는 제거된 제외한 이름이다.
     *  예) jpa.test = 14는
     *
     *   Properties 에
     *   test = 14 로 저장된다.
     * */
    @Bean
    @ConfigurationProperties("jpa")
    public Properties hibernateProperties()
    {
        Properties properties = new Properties();
        return properties;
    }

}
