package quartz;

import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class QuartzConfig {

    public SchedulerFactory createSchedulerFactory(String propertiesPath) throws IOException, SchedulerException {
        return  schedulerFactory(quartzProperties(propertiesPath));
    }

    public SchedulerFactory schedulerFactory(Properties properties) throws SchedulerException {
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory(properties);

        return  schedFact;
    }


    public Properties quartzProperties(String propertiesPath) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream(propertiesPath));
        return prop;

    }




}
