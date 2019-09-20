package quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;

import java.io.IOException;

public class AppStart {
    public static void main(String [] arg) throws IOException, SchedulerException {
        QuartzConfig quartzConfig = new QuartzConfig();
        SchedulerFactory schedulerFactory = quartzConfig.createSchedulerFactory("config/quartz.properties");
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
    }
}
