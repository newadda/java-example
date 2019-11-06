package simple;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        context.getFireTime();// 스케줄이 실제로 동작한 시간(스케줄이 동작해야 했던 시간보다 늦을수 있다.)
        context.getScheduledFireTime(); // 스케줄이 동작해야 했던 시간


        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.flush();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        System.out.flush();
    }
}
