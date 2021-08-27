package org.onecell.spring.jta.config;


import org.hibernate.SessionFactory;
import org.onecell.spring.jta.lib.DBKeyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
public  class XaDatabaseConfig {

    @Autowired
    @Qualifier("sessionFactory1")
    SessionFactory sessionFactory1;


    @Autowired
    @Qualifier("sessionFactory2")
    SessionFactory sessionFactory2;

    @Bean
    public DBKeyContext<String> dbKeyContext()
    {
        return new DBKeyContext();
    }


    @Primary
    @Bean
    public EntityManagerFactory routingSessionFactory()
    {
        RoutingSessionFactory routingSessionFactory = new RoutingSessionFactory(dbKeyContext());
        Map<Object,Object> map = new HashMap();
        map.put("1",sessionFactory1);
        map.put("2",sessionFactory2);

        routingSessionFactory.setTargetSessionFactories(map);
        return routingSessionFactory;
    }


}
