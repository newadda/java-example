package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class InjectJob implements Job {

    String injectTest;

    public void setInjectTest(String injectTest) {
        this.injectTest = injectTest;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
