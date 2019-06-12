package simple;

import org.junit.Test;
import org.quartz.*;
import quartz.QuartzConfig;

import java.io.IOException;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzSimpleTest {
    @Test
    public void test() throws IOException, SchedulerException {
        QuartzConfig quartzConfig = new QuartzConfig();
        SchedulerFactory schedulerFactory = quartzConfig.createSchedulerFactory("config/quartz.properties");
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();

        JobDetail job = newJob(HelloJob.class)
                .withIdentity("myJob", "group1")
                .build();


        Trigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();
        try {

            Date date = scheduler.scheduleJob(job, trigger);
        }catch (SchedulerException e)
        {

        }


        System.in.read();

    }

}
