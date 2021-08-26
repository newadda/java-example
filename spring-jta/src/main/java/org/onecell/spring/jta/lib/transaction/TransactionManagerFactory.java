package org.onecell.spring.jta.lib.transaction;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

public class TransactionManagerFactory {
    public HibernateTransactionManager createHibernateTransactionManger(SessionFactory sessionFactory)
    {

        HibernateTransactionManager txManager
                = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        txManager.afterPropertiesSet();
        return txManager;
    }




}
