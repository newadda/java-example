package org.onecellboy.db.hibernate;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.onecellboy.db.hibernate.table.People_Bi;
import org.onecellboy.db.hibernate.table.People_Uni;

public class NativeQuery {

	static SessionFactory sessionFactory = null;
	static StandardServiceRegistry registry = null;

	static int select_id = 0;
	
	
	@BeforeClass
	public static void setUp()
	{
		registry = new StandardServiceRegistryBuilder().configure(new File("./conf/hibernate/hibernate.cfg.xml")) 
				.build();
				sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

	}
	
	@AfterClass
	public static void setDown()
	{
		sessionFactory.close();
	}
	
	@After
	public void testAfter()
	{
		System.out.println();
		System.out.println();
	}
	
	@Test
	public void test() {
		
		Session session =null;
		Transaction tx = null;
		
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		
		org.hibernate.query.NativeQuery createNativeQuery = session.createNativeQuery("select * from people p");
		
		
		createNativeQuery.addEntity("p",People_Uni.class);
		List resultList = createNativeQuery.getResultList();
		
		for(Object rows : resultList)
		{
			Object[] list = (Object[]) rows;
			for(Object row : list)
			{
				System.out.print(row);
				System.out.print(" ");
			}
			System.out.println();
		}
		

		tx.commit();
		session.close();
		
		
	}

}
