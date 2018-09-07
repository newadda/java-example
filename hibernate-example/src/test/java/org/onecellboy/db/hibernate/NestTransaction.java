package org.onecellboy.db.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.vibur.dbcp.ViburDBCPDataSource;

import javax.sql.DataSource;
import java.io.File;

public class NestTransaction {

    static SessionFactory sessionFactory = null;
    static StandardServiceRegistry registry = null;

    static int select_id = 0;


    @BeforeClass
    public static void setUp()
    {
        registry = new StandardServiceRegistryBuilder().configure(new File("./conf/hibernate/hibernate_simple.cfg.xml"))
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    }

    @Test
    public void test1()
    {
        /**
         * 두번 이상의 트랜잭션 begin은 에러를 발산한다.
         *
         * java.lang.IllegalStateException: Transaction already active
         *
         * 	at org.hibernate.engine.transaction.internal.TransactionImpl.begin(TransactionImpl.java:52)
         * 	at org.hibernate.internal.AbstractSharedSessionContract.beginTransaction(AbstractSharedSessionContract.java:409)
         */
        Session session = sessionFactory.openSession();

        Transaction transaction1 = session.beginTransaction();

        Transaction transaction2 = session.beginTransaction();

    }

    @Test
    public void test2()
    {
        /**
         * openSession()은 새로운 세션을 만들고
         *
         * getCurrentSession()은 동일 쓰레드에서 동일한 세션을 반환한다.
         */

        Session session = sessionFactory.openSession();

        Session currentSession = sessionFactory.getCurrentSession();

    }


    @Test
    public void test3()
    {
        Session session = sessionFactory.openSession();
        Transaction transaction1 = session.beginTransaction();



        org.hibernate.query.NativeQuery createNativeQuery = session.createNativeQuery("insert into test(value) values(111)");
        createNativeQuery.executeUpdate();

        transaction1.commit();
        org.hibernate.query.NativeQuery createNativeQuery2 = session.createNativeQuery("insert into test(value) values(112)");
        createNativeQuery.executeUpdate();

        transaction1.commit();
    }


}
