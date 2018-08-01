package batch;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.jsr.configuration.xml.JobFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer 소스를 그대로 사용
 *
 * 설정 예를 보고 참고 했다.
 * batch.jdbc.driver=com.mysql.jdbc.Driver
 * batch.jdbc.url=jdbc:mysql://localhost/test
 * batch.jdbc.user=test
 * batch.jdbc.password=test
 * batch.jdbc.testWhileIdle=true
 * batch.jdbc.validationQuery=SELECT 1
 * batch.drop.script=classpath:/org/springframework/batch/core/schema-drop-mysql.sql
 * batch.schema.script=classpath:/org/springframework/batch/core/schema-mysql.sql
 * batch.business.schema.script=classpath:business-schema-mysql.sql
 * batch.database.incrementer.class=org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer
 * batch.database.incrementer.parent=columnIncrementerParent
 * batch.lob.handler.class=org.springframework.jdbc.support.lob.DefaultLobHandler
 * batch.jdbc.pool.size=6
 * batch.grid.size=50
 * batch.verify.cursor.position=true
 * batch.isolationlevel=ISOLATION_SERIALIZABLE
 * batch.table.prefix=BATCH_
 *
 *
 */
public class DBBatchConfig  implements  IBatchConfig{

    private final DataSource dataSource;

    private PlatformTransactionManager transactionManager;


    //// ===외부에 노출되는 인스턴스들===
    private JobRepository jobRepository;
    private JobLauncher jobLauncher;
    private JobExplorer jobExplorer;   // job을 확인할 수 있다.
    private StepBuilderFactory stepBuilderFactory;
    private JobBuilderFactory jobBuilderFactory;


    public DBBatchConfig(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        this.transactionManager = createTransactionManager();
        this.jobRepository=createJobRepository(this.dataSource,this.transactionManager);
        this.jobBuilderFactory=createJobBuilderFactory(this.jobRepository);
        this.stepBuilderFactory=createStepBuilderFactory(this.jobRepository,this.transactionManager);
        this.jobLauncher = createJobLauncher();
        this.jobExplorer =createJobExplorer();

    }

    @Override
    public JobRepository getJobRepository() {
        return jobRepository;
    }
    @Override
    public JobLauncher getJobLauncher() {
        return jobLauncher;
    }
    @Override
    public JobExplorer getJobExplorer() {
        return jobExplorer;
    }
    @Override
    public StepBuilderFactory getStepBuilderFactory() {
        return stepBuilderFactory;
    }
    @Override
    public JobBuilderFactory getJobBuilderFactory() {
        return jobBuilderFactory;
    }

    protected JobRepository createJobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.setIsolationLevelForCreate("ISOLATION_DEFAULT");
        //// DataSource 만 설정되면 아래는 기본적인 값으로 설정된다.
        // jobRepositoryFactoryBean.setJdbcOperations();
        // jobRepositoryFactoryBean.setIncrementerFactory();
        // jobRepositoryFactoryBean.setLobHandler();
        // jobRepositoryFactoryBean.setMaxVarCharLength();
        // jobRepositoryFactoryBean.setTablePrefix();
         // jobRepositoryFactoryBean.setClobType();
        /*if (this.entityManagerFactory != null) {
            logger.warn(
                    "JPA does not support custom isolation levels, so locks may not be taken when launching Jobs");
            factory.setIsolationLevelForCreate("ISOLATION_DEFAULT");
        }

        String tablePrefix = this.properties.getTablePrefix();
        if (StringUtils.hasText(tablePrefix)) {
            factory.setTablePrefix(tablePrefix);
        }
        */

        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
        jobRepositoryFactoryBean.afterPropertiesSet();
        return jobRepositoryFactoryBean.getObject();
    }


    protected StepBuilderFactory createStepBuilderFactory(JobRepository jobRepository, PlatformTransactionManager transactionManager)
    {

        return new StepBuilderFactory(jobRepository,transactionManager);

    }




    protected JobBuilderFactory createJobBuilderFactory(JobRepository jobRepository)
    {

        return new JobBuilderFactory(jobRepository);
    }


    protected PlatformTransactionManager createTransactionManager() {
        PlatformTransactionManager transactionManager = createAppropriateTransactionManager();



        return transactionManager;
    }

    protected PlatformTransactionManager createAppropriateTransactionManager() {
        /*
        if (this.entityManagerFactory != null) {
            return new JpaTransactionManager(this.entityManagerFactory);
        }
        */
        return new DataSourceTransactionManager(this.dataSource);
    }

    protected JobExplorer createJobExplorer() throws Exception {
        JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
        jobExplorerFactoryBean.setDataSource(this.dataSource);
        jobExplorerFactoryBean.afterPropertiesSet();

        return jobExplorerFactoryBean.getObject();
    }

    protected JobLauncher createJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(getJobRepository());

        // Thread Pool 설정
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(4);
        threadPoolTaskExecutor.setMaxPoolSize(8);
        threadPoolTaskExecutor.setQueueCapacity(32);
        threadPoolTaskExecutor.initialize();
        jobLauncher.setTaskExecutor(threadPoolTaskExecutor);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

/*
    protected JobOperator craeteJobOperator() {
        SimpleJobOperator simpleJobOperator = new SimpleJobOperator();
        //  simpleJobOperator.
        return null;
    }*/


}
