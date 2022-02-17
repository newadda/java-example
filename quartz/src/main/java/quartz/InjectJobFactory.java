package quartz;

import job.InjectJob;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;

public class InjectJobFactory extends SimpleJobFactory {
    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler) throws SchedulerException {
        Job job = super.newJob(bundle, Scheduler);
        if(job.getClass().isAssignableFrom(InjectJob.class))
        {
            ((InjectJob)job).setInjectTest("injection");;
        }
        return job;

    }
}
