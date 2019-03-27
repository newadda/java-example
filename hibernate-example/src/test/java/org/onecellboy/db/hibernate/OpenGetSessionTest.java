package org.onecellboy.db.hibernate;

import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.orm.hibernate5.SpringSessionContext;


import java.io.File;
import java.util.Objects;

public class OpenGetSessionTest {

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

    /**
     * openSession() 은 새로운 세션을 반환한다.
     * getCurrentSession() 은 쓰레드당 동일한 하나의 세션을 반환한다. 두번 세번 호출해도 동일한 세션을 반환한다.
     * 하지만 세션을 닫으면 또 다른 세션을 반환한다. 중요한 것은 쓰레드에 동일한 세션을 반환한다는 것
     */
    @Test
    public void test()
    {
        Session session0= sessionFactory.openSession();
        Session session1 = sessionFactory.getCurrentSession();

        Session session2= sessionFactory.openSession();

        Session session3 = sessionFactory.getCurrentSession();
        Transaction transaction = session3.beginTransaction();



    }

}
