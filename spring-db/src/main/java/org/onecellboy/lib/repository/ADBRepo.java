package org.onecellboy.lib.repository;

import org.hibernate.SessionFactory;
import org.springframework.transaction.PlatformTransactionManager;

public abstract class ADBRepo {
    protected SessionFactory entityManagerFactory;
    protected PlatformTransactionManager platformTransactionManager;

    public ADBRepo(SessionFactory entityManagerFactory, PlatformTransactionManager platformTransactionManager) {
        this.entityManagerFactory = entityManagerFactory;
        this.platformTransactionManager = platformTransactionManager;
    }

    public SessionFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public PlatformTransactionManager getPlatformTransactionManager() {
        return platformTransactionManager;
    }
}
