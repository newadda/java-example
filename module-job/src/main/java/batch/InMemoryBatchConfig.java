package batch;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.SimpleJobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

public class InMemoryBatchConfig implements IBatchConfig {

    private PlatformTransactionManager transactionManager;

    //// ===외부에 노출되는 인스턴스들===
    private JobRepository jobRepository;
    private JobLauncher jobLauncher;
    private JobExplorer jobExplorer;   // job을 확인할 수 있다.
    private StepBuilderFactory stepBuilderFactory;
    private JobBuilderFactory jobBuilderFactory;

    public InMemoryBatchConfig() throws Exception {
        this.transactionManager=new ResourcelessTransactionManager();
        MapJobRepositoryFactoryBean mapJobRepositoryFactory = createMapJobRepositoryFactory(this.transactionManager);
        this.jobRepository = createJobRepository(mapJobRepositoryFactory);
        this.jobLauncher = createJobLauncher(this.jobRepository);
        this.jobExplorer = createJobExplorer(mapJobRepositoryFactory);
        this.stepBuilderFactory =createStepBuilderFactory(this.jobRepository,this.transactionManager);
        this.jobBuilderFactory = createJobBuilderFactory(this.jobRepository);
    }

    protected MapJobRepositoryFactoryBean createMapJobRepositoryFactory(PlatformTransactionManager txManager)
            throws Exception {
        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean(txManager);
        factory.afterPropertiesSet();
        return factory;
    }

    public JobRepository createJobRepository(MapJobRepositoryFactoryBean factory) throws Exception {
        return factory.getObject();
    }

    protected JobExplorer createJobExplorer(MapJobRepositoryFactoryBean factory) {
        return new SimpleJobExplorer(factory.getJobInstanceDao(), factory.getJobExecutionDao(),
                factory.getStepExecutionDao(), factory.getExecutionContextDao());
    }

    protected SimpleJobLauncher createJobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        return launcher;
    }


    protected StepBuilderFactory createStepBuilderFactory(JobRepository jobRepository, PlatformTransactionManager transactionManager)
    {
        return new StepBuilderFactory(jobRepository,transactionManager);
    }


    protected JobBuilderFactory createJobBuilderFactory(JobRepository jobRepository)
    {
        return new JobBuilderFactory(jobRepository);
    }


    @Override
    public JobRepository getJobRepository() {
        return null;
    }

    @Override
    public JobLauncher getJobLauncher() {
        return null;
    }

    @Override
    public JobExplorer getJobExplorer() {
        return null;
    }

    @Override
    public StepBuilderFactory getStepBuilderFactory() {
        return null;
    }

    @Override
    public JobBuilderFactory getJobBuilderFactory() {
        return null;
    }
}
