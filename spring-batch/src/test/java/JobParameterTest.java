import batch.DBBatchConfig;
import batch.DBManager;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.sun.istack.internal.NotNull;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.vibur.dbcp.ViburDBCPDataSource;
import util.JobParameterTransformer;
import util.TaskletParamTransformer;

import javax.sql.DataSource;
import java.time.LocalDateTime;

public class JobParameterTest {
    public static class JobParams {
        @NotNull
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime job_since;
        @NotNull
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime job_until;


        public LocalDateTime getJob_since() {
            return job_since;
        }

        public void setJob_since(LocalDateTime job_since) {
            this.job_since = job_since;
        }

        public LocalDateTime getJob_until() {
            return job_until;
        }

        public void setJob_until(LocalDateTime job_until) {
            this.job_until = job_until;
        }
    }


    public static class StepParams{
        public LocalDateTime SP_FPH_SINCE_DATETIME; //시작 이상
        public LocalDateTime SP_FPH_UNTIL_DATETIME; // 끝 이하
    }





    public static class MyTasklet implements Tasklet {



        private  int i=0;
        @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

            /**
             * Step 파라미터 역직렬화
             *
             * Step 이 공유하는 BATCH_JOB_EXECUTION_CONTEXT에 저장된 데이터를 주어진 클래스(예. StepParams.class) 로 객체화 한다.
             *
             */
            ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();

            TaskletParamTransformer transformer = new TaskletParamTransformer();

            StepParams params = transformer.fromExecutionContext(StepParams.class, executionContext);

            LocalDateTime start_dt = params.SP_FPH_SINCE_DATETIME;
            LocalDateTime end_dt = params.SP_FPH_UNTIL_DATETIME;



                return RepeatStatus.FINISHED;



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


    /**
     *
     * JOB 과 STEP 의 연결성을 줄이기 위해 STEP에 파라미터를 전달할 수 있게 한 것이다.
     *
     * 또한 이를 편하게 하기 위해서 객체를 STEP 에 전달가능하게 하고 STEP의 파라미터를 객체로도 만들수 있게 유틸을 만들었다.
     *
     *
     *
     *
     */
    // @Test
    public void parameterTest() throws Exception {
        DataSource dataSource = getDataSource("jdbc:mysql://192.168.0.2:3306/oauth2?useSSL=false&characterEncoding=utf-8", "com.mysql.cj.jdbc.Driver"
                , "shh", "shh");
        DBManager dbManager = new DBManager(dataSource);

        DBBatchConfig DBBatchConfig = new DBBatchConfig(dataSource);

        JobBuilderFactory jobBuilderFactory = DBBatchConfig.getJobBuilderFactory();

        StepBuilderFactory stepBuilderFactory = DBBatchConfig.getStepBuilderFactory();

        JobOperator jobOperator = DBBatchConfig.getJobOperator();

        JobRegistry jobRegistry = DBBatchConfig.getJobRegistry();

        JobExplorer jobExplorer = DBBatchConfig.getJobExplorer();




        TaskletStep step1 = stepBuilderFactory.get("step1").tasklet(new MyTasklet())
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {

                        /// Job Parameter 를 가져온다.
                        JobParameters jobParameters = stepExecution.getJobParameters();

                        /// Job Parameter 를 기반으로 Step Parameter를 만들어 Step 에 넣는것이다.
                        StepParams parameter = new StepParams();
                        //// 해당 Step Execution 에 Parameter를 넣으면 step 에서만 접근가능해 진다.
                        stepExecution.getExecutionContext().put("KEY",parameter);
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        return null;
                    }
                })

                .build();

        TaskletStep step2 = stepBuilderFactory.get("step2").tasklet(new MyTasklet()).build();
        Job test = jobBuilderFactory.get("test").start(step1).next(step2)
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {

                        /**
                         * JOB 파라미터 역직렬화
                         */

                        JobParams jobParams = JobParameterTransformer.fromMap(jobExecution.getJobParameters(), JobParams.class);

                        LocalDateTime job_since = jobParams.getJob_since();
                        LocalDateTime job_until = jobParams.getJob_until();


                        /**
                         * Step 파라미터 직렬화
                         * Step 이 공유하는 BATCH_JOB_EXECUTION_CONTEXT에 저장한다.
                         */
                        ExecutionContext executionContext = jobExecution.getExecutionContext();

                        TaskletParamTransformer transformer = new TaskletParamTransformer();
                        StepParams params = new StepParams();

                        params.SP_FPH_SINCE_DATETIME=jobParams.getJob_since();
                        params.SP_FPH_UNTIL_DATETIME=jobParams.getJob_until();

                        transformer.toExecutionContext(executionContext,params);




                        System.out.println("beforeJob");
                    }



                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        System.out.println("afterJob");
                    }
                }).build();

        JobLauncher jobLauncher = DBBatchConfig.getJobLauncher();


        ////
        //// 파라미터 클래스
        JobParams jobParams =new JobParams();
        jobParams.setJob_since(LocalDateTime.now());
        jobParams.setJob_until(LocalDateTime.now());

        /// 파라미터 직렬화
        JobParameters jobParameters = JobParameterTransformer.toJobParameters(jobParams);

        /*
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        */



        jobRegistry.register(new ReferenceJobFactory(test));
        JobExecution run = jobLauncher.run(test, jobParameters);


        Thread.sleep(5000);


        try {
            run.stop();
        }finally {

        }

        // jobOperator.stop(run.getJobId());


        JobExecution jobExecution = jobExplorer.getJobExecution(run.getJobId());
        Long time = jobExecution.getJobParameters().getLong("time");
        System.out.println("para ="+time);


        System.in.read();
    }

}
