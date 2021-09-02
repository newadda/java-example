package org.onecell.saga.bank.config.axon;

import org.axonframework.axonserver.connector.AxonServerConfiguration;
import org.axonframework.axonserver.connector.event.axon.AxonServerEventStore;
import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

public class Util {
    /// 스프링 트랜잭션을 Axon 트랜잭션으로 바꿈
    public static TransactionManager springTransactionManagerToAxonTransactionManager(PlatformTransactionManager springPlatformTransactionManager) {
        return new SpringTransactionManager(springPlatformTransactionManager);



    }

        public AxonServerEventStore axonServerEventStore() {
            AxonServerEventStore axonServerEventStore = AxonServerEventStore.builder().configuration(new AxonServerConfiguration() {
                {
                    setServers("localhost");
                }
            }).build();

            return axonServerEventStore;
        }


}
