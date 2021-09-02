package org.onecell.saga.bank.config;


import lombok.RequiredArgsConstructor;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.hibernate.SessionFactory;
import org.onecell.saga.bank.aggregates.AccountAggregate;
import org.onecell.saga.bank.config.axon.hibernate.ConfigurerCreator;
import org.onecell.saga.bank.config.axon.hibernate.HibernateEntityManagerProvider;
import org.onecell.saga.bank.config.axon.hibernate.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 최대한 Axon 기능을 사용하려고 했다.
 */


@Configuration
@RequiredArgsConstructor
public class AxonConfig {

    @Autowired
    HibernateTransactionManager transactionManager;

    @Autowired
    SessionFactory sessionFactory;

    @Bean
    public Configurer configurer()
    {
        Parameter parameter = new Parameter();
        parameter.setSessionFactory(sessionFactory);
        parameter.setSpringPlatformTransactionManager(transactionManager);
        parameter.getAggregateList().add(AccountAggregate.class);
        parameter.getEventHandlerList().add(configuration -> new AccountAggregate());

        ConfigurerCreator configurerCreator = new ConfigurerCreator(parameter);
        Configurer configurer = configurerCreator.getConfigurer();
        return configurer;
    }


}
