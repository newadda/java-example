package org.onecell.saga.bank.config.axon.hibernate;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.config.Configuration;
import org.hibernate.SessionFactory;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@Setter
@Getter
public class Parameter {
    SessionFactory sessionFactory;
    PlatformTransactionManager springPlatformTransactionManager;
    List<Class> aggregateList = new LinkedList<>();
    List<Function<Configuration, Object>> eventHandlerList = new LinkedList<>();

}
