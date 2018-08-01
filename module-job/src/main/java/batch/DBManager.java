package batch;


import org.springframework.batch.support.DatabaseType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.jdbc.support.incrementer.*;
import org.springframework.util.Assert;

import javax.sql.DataSource;

import static org.springframework.batch.support.DatabaseType.*;
import static org.springframework.batch.support.DatabaseType.SQLSERVER;
import static org.springframework.batch.support.DatabaseType.SYBASE;

public class DBManager {
    String createSchemaScript= "/org/springframework/batch/core/schema-mysql.sql";
    String dropSchemaScript = "/org/springframework/batch/core/schema-drop-mysql.sql";


    private DataSource dataSource;
    private  DataSourceInitializer dataSourceInitializer;
    public DBManager(DataSource dataSource) throws MetaDataAccessException {
        Assert.notNull(dataSource, "DataSource must not be null.");


        // DefaultDataFieldMaxValueIncrementerFactory 의 소스를 참고함
        DatabaseType databaseType = DatabaseType.fromMetaData(dataSource);
        if (databaseType == DB2 || databaseType == DB2AS400) {
            this.createSchemaScript= "/org/springframework/batch/core/schema-db2.sql";
            this.dropSchemaScript = "/org/springframework/batch/core/schema-drop-db2.sql";
        }
        else if (databaseType == DB2ZOS) {
            this.createSchemaScript= "/org/springframework/batch/core/schema-db2.sql";
            this.dropSchemaScript = "/org/springframework/batch/core/schema-drop-db2.sql";
        }
        else if (databaseType == DERBY) {
            this.createSchemaScript= "/org/springframework/batch/core/schema-derby.sql";
            this.dropSchemaScript = "/org/springframework/batch/core/schema-drop-derby.sql";
        }
        else if (databaseType == HSQL) {
            this.createSchemaScript= "/org/springframework/batch/core/schema-hsqldb.sql";
            this.dropSchemaScript = "/org/springframework/batch/core/schema-drop-hsqldb.sql";
        }
        else if (databaseType == H2) {
            this.createSchemaScript= "/org/springframework/batch/core/schema-h2.sql";
            this.dropSchemaScript = "/org/springframework/batch/core/schema-drop-h2.sql";
        }
        else if (databaseType == MYSQL) {
            this.createSchemaScript= "/org/springframework/batch/core/schema-mysql.sql";
            this.dropSchemaScript = "/org/springframework/batch/core/schema-drop-mysql.sql";
        }
        else if (databaseType == ORACLE) {
            this.createSchemaScript= "/org/springframework/batch/core/schema-oracle10g.sql";
            this.dropSchemaScript = "/org/springframework/batch/core/schema-drop-oracle10g.sql";
        }
        else if (databaseType == POSTGRES) {
            this.createSchemaScript= "/org/springframework/batch/core/schema-postgresql.sql";
            this.dropSchemaScript = "/org/springframework/batch/core/schema-drop-postgresql.sql";
        }
        else if (databaseType == SQLITE) {
            this.createSchemaScript= "/org/springframework/batch/core/schema-sqlite.sql";
            this.dropSchemaScript = "/org/springframework/batch/core/schema-drop-sqlite.sql";
        }
        else if (databaseType == SQLSERVER) {
            this.createSchemaScript= "/org/springframework/batch/core/schema-sqlserver.sql";
            this.dropSchemaScript = "/org/springframework/batch/core/schema-drop-sqlserver.sql";
        }
        else if (databaseType == SYBASE) {
            this.createSchemaScript= "/org/springframework/batch/core/schema-sybase.sql";
            this.dropSchemaScript = "/org/springframework/batch/core/schema-drop-sybase.sql";
        }else
        {
            throw new IllegalArgumentException("databaseType argument was not on the approved list");
        }


        this.dataSourceInitializer = createDataSourceInitializer(dataSource,createSchemaScript,dropSchemaScript);

    }

    /**
     *
     * @param dataSource
     * @param createSchemaScript
     * @param dropSchemaScript
     * @return
     */
    protected DataSourceInitializer createDataSourceInitializer(DataSource dataSource,String createSchemaScript,String dropSchemaScript)
    {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);

        Resource create = new ClassPathResource(createSchemaScript);
        dataSourceInitializer.setDatabasePopulator(new ResourceDatabasePopulator(create));

        Resource drop = new ClassPathResource(dropSchemaScript);
        dataSourceInitializer.setDatabaseCleaner(new ResourceDatabasePopulator(drop));
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
