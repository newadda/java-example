package quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.simpl.SimpleJobFactory;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;

public class AppStart {
    public static void main(String [] arg) throws IOException, SchedulerException {
        QuartzConfig quartzConfig = new QuartzConfig();
        SchedulerFactory schedulerFactory = quartzConfig.createSchedulerFactory("config/quartz.properties");
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.setJobFactory(new InjectJobFactory());
        scheduler.start();
    }
}
