package org.onecell.spring.template.lib.db;

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
