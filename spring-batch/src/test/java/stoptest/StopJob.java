package stoptest;

import batch.DBBatchConfig;
import batch.DBManager;
import org.junit.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.vibur.dbcp.ViburDBCPDataSource;

import javax.sql.DataSource;

public class StopJob {
    /**
     * DataSource 설정
     * @param datasourceUrl
     * @param dbDriverClassName
     * @param dbUsername
     * @param dbPassword
     * @return
     */
    public DataSource getDataSource(String datasourceUrl,
                                    String dbDriverClassName,
                                    String dbUsername,
                                    String dbPassword)
    {
        ViburDBCPDataSource dataSource = new ViburDBCPDataSource();

        dataSource.setJdbcUrl(datasourceUrl);
        dataSource.setDriverClassName(dbDriverClassName);
        // dataSource.setUrl(datasourceUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        dataSource.setPoolInitialSize(10);
        dataSource.setPoolMaxSize(100);

        dataSource.setConnectionIdleLimitInSeconds(30);


        dataSource.setLogQueryExecutionLongerThanMs(500);
        dataSource.setLogStackTraceForLongQueryExecution(true);

        dataSource.setStatementCacheMaxSize(200);

        dataSource.start();

        return dataSource;
    }

    @Test
    public void test() throws Exception {
        DataSource dataSource = getDataSource("jdbc:mysql://192.168.0.92:3306/oauth2?useSSL=false&characterEncoding=utf-8", "com.mysql.cj.jdbc.Driver"
                , "shh", "shh");
        DBManager dbManager = new DBManager(dataSource);

        batch.DBBatchConfig DBBatchConfig = new DBBatchConfig(dataSource);

        JobBuilderFactory jobBuilderFactory = DBBatchConfig.getJobBuilderFactory();

        StepBuilderFactory stepBuilderFactory = DBBatchConfig.getStepBuilderFactory();

        JobOperator jobOperator = DBBatchConfig.getJobOperator();

        JobRegistry jobRegistry = DBBatchConfig.getJobRegistry();

        JobExplorer jobExplorer = DBBatchConfig.getJobExplorer();

        JobLauncher jobLauncher = DBBatchConfig.getJobLauncher();

        TaskletStep step1 = stepBuilderFactory.get("step1").tasklet(new StartJob.MyTasklet()).build();

        Job test = jobBuilderFactory.get("startstoptest").start(step1)
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        System.out.println("beforeJob");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        System.out.println("afterJob");
                    }
                }).build();

        jobRegistry.register(new ReferenceJobFactory(test));

        JobParameters jobParameters = new JobParametersBuilder().addLong("time1", 99l,false)
                .toJobParameters();

        JobExecution run = jobLauncher.run(test, jobParameters);

        //JobExecution jobExecution = jobExplorer.getJobExecution(136l);
        //jobExecution.stop();

       // jobOperator.abandon(137l);
       // jobOperator.stop(147l);

       // jobOperator.restart(147l);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
