package org.onecell.spring.jta.lib.transaction;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.hibernate.SessionFactory;
import org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

public class TransactionManagerFactory {
    public HibernateTransactionManager createHibernateTransactionManger(SessionFactory sessionFactory)
    {

        HibernateTransactionManager txManager
                = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        txManager.afterPropertiesSet();
        return txManager;
    }


    public UserTransaction createUserTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(10000);

        return userTransactionImp;
    }

    public TransactionManager createAtomikosTransactionManager() throws Throwable {

        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }


    public PlatformTransactionManager transactionManager() throws Throwable {
        UserTransaction userTransaction = createUserTransaction();

        JtaTransactionManager manager = new JtaTransactionManager(userTransaction, createAtomikosTransactionManager());
        return manager;
    }


}
