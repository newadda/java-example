package org.onecell.spring.jta.lib.datasource;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.onecell.spring.jta.lib.property.DatabaseProp;

import java.util.Properties;

public class DataSourceBeanFactory {
    public static AtomikosDataSourceBean atomikosDataSourceBean(String resourceName, DatabaseProp databaseProp)
    {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();

        ds.setUniqueResourceName(resourceName);


        ds.setXaDataSourceClassName(databaseProp.getDriverClassName());
        Properties p = new Properties();
        p.setProperty("user", databaseProp.getUsername());
        p.setProperty("password", databaseProp.getPassword());
        p.setProperty("url", databaseProp.getUrl());
        if(databaseProp.getDefaultSchema()!=null)
        {
            p.setProperty("databaseName", databaseProp.getDefaultSchema());
        }

        p.setProperty("pinGlobalTxToPhysicalConnection", "true"); // Mysql의 XAER_INVAL: Invalid arguments (or unsupported command)를 해결한다.


        ds.setXaProperties (p);

        return  ds;

    }

}
