package org.onecell.saga.bank.config.axon.hibernate;

import org.axonframework.axonserver.connector.AxonServerConfiguration;
import org.axonframework.axonserver.connector.event.axon.AxonServerEventStore;
import org.axonframework.config.Configuration;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.hibernate.SessionFactory;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.function.Function;

import static org.onecell.saga.bank.config.axon.Util.springTransactionManagerToAxonTransactionManager;

public class ConfigurerCreator {
    Configurer configurer;
    Parameter parameter;
    public ConfigurerCreator(Parameter parameter) {
        this.parameter=parameter;
        PlatformTransactionManager springPlatformTransactionManager = parameter.getSpringPlatformTransactionManager();
        SessionFactory sessionFactory = parameter.getSessionFactory();
        configurer = DefaultConfigurer.jpaConfiguration(new HibernateEntityManagerProvider(sessionFactory)
                , springTransactionManagerToAxonTransactionManager(springPlatformTransactionManager));
    }

    public Configurer getConfigurer() {
        return configurer;
    }

    private void registerAggregate()
    {
        List<Class> aggregateList = parameter.getAggregateList();
        for(Class aggregate:aggregateList)
        {
            configurer.configureAggregate(aggregate);
        }
    }

    private void registerEventHandler()
    {
        List<Function<Configuration, Object>> eventHandlerList = parameter.getEventHandlerList();

    }






}
