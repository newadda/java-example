package org.onecellboy.db.hibernate;

import org.vibur.dbcp.ViburDBCPDataSource;

public class ViburDatasourceCreate {
    public void viburCreate(){
        ViburDBCPDataSource dataSource = new ViburDBCPDataSource();

        dataSource.setJdbcUrl("jdbc:mysql://192.168.1.22:3306/WATERWORKS?useSSL=false&characterEncoding=utf-8&autoReconnect=true");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // dataSource.setUrl(datasourceUrl);
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        dataSource.setPoolInitialSize(30);
        dataSource.setPoolMaxSize(100);


        dataSource.setConnectionTimeoutInMs(15000);
        dataSource.setConnectionIdleLimitInSeconds(10);
        dataSource.setTestConnectionQuery("select 1");

        dataSource.setLogQueryExecutionLongerThanMs(500); //쿼리가 해당시간보다 길게 늦어지면 로깅하는 시간
        dataSource.setLogStackTraceForLongQueryExecution(true);// 쿼리가 길게 늦어지면 로깅할 것인지
        dataSource.setStatementCacheMaxSize(200);//쿼리 캐시 사이즈

        dataSource.start();


    }
}
