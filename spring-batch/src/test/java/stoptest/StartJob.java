package stoptest;

import batch.DBBatchConfig;
import batch.DBManager;
import org.junit.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.vibur.dbcp.ViburDBCPDataSource;

import javax.sql.DataSource;

public class StartJob {

    public StartJob() throws Exception {
    }

    /**
     * 10초간 진행되는 작업
     */
    public static class MyTasklet implements Tasklet {
        private  int i=0;
        @Override
        public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {



            int commitCount = arg1.getStepContext().getStepExecution().getCommitCount();
            int skipCount = arg0.getSkipCount();

            System.out.println("Hello This is a MyTasklet : "+commitCount);
            System.out.println("Hello This is a MyTasklet : "+skipCount);
            Thread.sleep(1000);
            i++;
            if(i<=10) {
                System.out.println("Hello This is a CONTINUABLE i=  : "+i);
                return RepeatStatus.CONTINUABLE;
            }else
            {
                return RepeatStatus.FINISHED;
            }


        }
    }

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




        TaskletStep step1 = stepBuilderFactory.get("step1").tasklet(new MyTasklet()).build();
        TaskletStep step2 = stepBuilderFactory.get("step2").tasklet(new MyTasklet()).build();

        Job test = jobBuilderFactory.get("startstoptest").start(step1).next(step2)
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
        JobParameters jobParameters = new JobParametersBuilder().addLong("time1", 60l)
                .toJobParameters();



        JobExecution run = jobLauncher.run(test, jobParameters);


        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




}
