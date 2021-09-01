package org.onecell.saga.bank.config.axon;


import lombok.RequiredArgsConstructor;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.onecell.saga.bank.config.axon.hibernate.HibernateEntityManagerProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 최대한 Axon 기능을 사용하려고 했다.
 */


@Configuration
@RequiredArgsConstructor
public class AxonConfig {

    Configurer configurer;
    public void AxonConfig()
    {
        DefaultConfigurer.jpaConfiguration(new HibernateEntityManagerProvider(), springTransactionManagerToAxonTransactionManager())
    }



    public void configureAggregate()
    {
        // configurer.configureAggregate(TestAggregate.class)
        // 혹은 저장소에 Aggregate 를 저장해야할 때
        /*
        Configurer configurer = DefaultConfigurer.defaultConfiguration()
                .configureAggregate(
                        AggregateConfigurer.defaultConfiguration(TestAggregate.class).
                                .configureRepository(c -> EventSourcingRepository.builder(TestAggregate.class)
                                        .eventStore(c.eventStore())
                                        .build())
                );
        */

    }

    public void configureEvent()
    {

        //configurer.registerEventHandler(config -> new MyEventHandlingComponent());
    }



    public void createJpaConfigure()
    {

    }




    /*
    final PlatformTransactionManager platformTransactionManager;

    @Bean
    public TransactionManager springTransactionManager() {
        return new SpringTransactionManager(platformTransactionManager);
    }

    @Bean // don't wary, aggregator will be stored in persistent db...
    public EventStorageEngine eventStorageEngine() {
        return new InMemoryEventStorageEngine();
    }

    @Bean(name = "accountRepository")
    public Repository<Account> accountRepository(final EventBus eventBus) {
        return new GenericJpaRepository<>(axonEntityManagerProvider(), Account.class, eventBus);
    }

    @Bean
    public EntityManagerProvider axonEntityManagerProvider() {
        return new ContainerManagedEntityManagerProvider();
    }

    @Bean
    public CommandBus asynchronousCommandBus() {
        SimpleCommandBus commandBus = new AsynchronousCommandBus();
        commandBus.registerHandlerInterceptor(new TransactionManagingInterceptor(springTransactionManager()));
        return commandBus;
    }

     */
}
