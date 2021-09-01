package org.onecell.saga.bank.config.axon.hibernate;

import org.axonframework.common.jpa.EntityManagerProvider;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;

public class HibernateEntityManagerProvider implements EntityManagerProvider {
    SessionFactory sessionFactory;
    public HibernateEntityManagerProvider(SessionFactory sessionFactory)
    {
        this.sessionFactory=sessionFactory;
    }


    @Override
    public EntityManager getEntityManager() {
        return this.sessionFactory.getCurrentSession();
    }
}
