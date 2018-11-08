package org.onecellboy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.onecellboy.config.PersistenceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Properties;



@ComponentScan(basePackages = {"org.onecellboy.config"},
        basePackageClasses = PersistenceConfig.class)
public class Application {


    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    PlatformTransactionManager transactionManager;

    public static void main(String[] args)
    {




        AnnotationConfigApplicationContext applicationContextcontext = new AnnotationConfigApplicationContext();
        // AnnotationConfigApplicationContext applicationContextcontext = new AnnotationConfigApplicationContext(Application.class);

        /**
         * ------- 코드로 properties 를 등록할 수 있다. ---------
         */
        applicationContextcontext.register(Application.class);
        PropertyPlaceholderConfigurer propConfig = new PropertyPlaceholderConfigurer();
        Properties properties = new Properties();
        properties.put("database.driverClassName","com.mysql.jdbc.Driver");
        properties.put("datasource.username","shh");
        properties.put("datasource.url","jdbc:mysql://192.168.1.22:3306/DB_TEST?createDatabaseIfNotExist=false");
        properties.put("datasource.password","shh");

        propConfig.setProperties(properties);
        applicationContextcontext.addBeanFactoryPostProcessor(propConfig);
        applicationContextcontext.refresh();

        /**
         *  --------------------------------
         */


        Application bean = applicationContextcontext.getBean(Application.class);

        try {
            bean.run2();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    bean.run();
                }
            });
            t.start();
            bean.run();
            bean.run();
            bean.run();

         }catch(Exception e)
        {
            System.out.println("Error in creating record, rolling back");
        }

    }

    @Transactional
    public void run()
    {
        Session currentSession = sessionFactory.getCurrentSession();


        NativeQuery sqlQuery = currentSession.createSQLQuery("select * from test");
        List<Object> rows = sqlQuery.list();
        for(Object row : rows){

            System.out.println(row.toString());
        }



    }

    public void run2()
    {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);


        Session currentSession=null;
        try {
             currentSession = sessionFactory.getCurrentSession();

            NativeQuery sqlQuery = currentSession.createSQLQuery("select * from test");
            List<Object> rows = sqlQuery.list();
            for (Object row : rows) {

                System.out.println(row.toString());
            }
            transactionManager.commit(status);
        }catch (DataAccessException e) {
                System.out.println("Error in creating record, rolling back");
                transactionManager.rollback(status);
                throw e;
            }
        currentSession.close();

    }


}
