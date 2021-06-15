package org.onecell.spring.template.db;

import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class SessionFactoryBeanFactory {
   public LocalSessionFactoryBean createHibernateLocalSessionFactoryBean(DataSource dataSource, String[] packagesScanPath, Properties properties)
   {
       LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
       sessionFactory.setDataSource(dataSource);
       sessionFactory.setPackagesToScan(
               packagesScanPath);
       sessionFactory.setHibernateProperties(properties);
       try {
           sessionFactory.afterPropertiesSet();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return sessionFactory;
   }

    public LocalSessionFactoryBean createHibernateLocalSessionFactoryBean(DataSource dataSource )
    {
        return createHibernateLocalSessionFactoryBean(dataSource, defaultMappingClass(), hibernateDefaultProperties());
    }


    public LocalSessionFactoryBean createHibernateLocalSessionFactoryBean(DataSource dataSource, String[] packagesScanPath)
    {
       return createHibernateLocalSessionFactoryBean(dataSource, packagesScanPath, hibernateDefaultProperties());
    }

    String [] defaultMappingClass()
    {
        return new String[]{"org.waterworks.lib.db.dto.user"};

    }


    /**
     * =hibernate.hbm2ddl.auto =
     * none : 기본 값이며 아무 일도 일어나지 않는다.
     * create-only : 데이터베이스를 새로 생성한다.
     * drop : 데이터베이스를 drop 한다.
     * create : 데이터베이스를 drop 한 후, 데이터베이스를 새로 생성한다.(기능적으로는 drop + create-only와 같다)
     * create-drop : SessionFactory가 시작될 때 스키마를 drop하고 재생성하며, SessionFactory가 종료될 때도 스키마를 drop 한다.
     * validate : 데이터베이스 스키마를 검증 한다.
     * update : 데이터베이스 스키마를 갱신 한다.
     *
     *
     * @return
     */
    Properties hibernateDefaultProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto",
                    "none");
                setProperty("hibernate.show_sql",
                        "true");
                setProperty("hibernate.dialect",
                       "org.hibernate.dialect.MySQL57Dialect");
                setProperty("hibernate.jdbc.time_zone",
                        "Asia/Seoul");
                setProperty("format_sql",
                        "true");
                setProperty("show_sql",
                        "true");
                setProperty("use_sql_comments",
                        "true");
            }
        };
    }

}
