package org.onecellboy.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public class DBInitializer {
    DataSourceInitializer dataSourceInitializer;

    public DBInitializer(DataSource dataSource, String createSchemaScript, String dropSchemaScript) {
        this.dataSourceInitializer = createDataSourceInitializer(dataSource,createSchemaScript,dropSchemaScript);
    }

    protected DataSourceInitializer createDataSourceInitializer(DataSource dataSource, String createSchemaScript, String dropSchemaScript)
    {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);

        Resource create = new ClassPathResource(createSchemaScript);
        dataSourceInitializer.setDatabasePopulator(new ResourceDatabasePopulator(create));

        if(dropSchemaScript!=null) {
            Resource drop = new ClassPathResource(dropSchemaScript);
            dataSourceInitializer.setDatabaseCleaner(new ResourceDatabasePopulator(drop));
        }

        return dataSourceInitializer;
    }


    public void createTable()
    {
        this.dataSourceInitializer.afterPropertiesSet();
    }

    public void dropTable()
    {
        this.dataSourceInitializer.destroy();
    }
}
