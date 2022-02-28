package com.spring.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author satish
 * For more info please visit:
 * https://www.baeldung.com/spring-scheduled-tasks#schedule-a-task-at-a-fixed-rate
 */
@Component
@EnableAsync
public class SchedulerExamples {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	/**
	 * Fixed rate is nothing but to run the scheduler at fixed interval of time.
	 * Here the below method runs for every 5 seconds.
	 * This option should be used when each execution of the task is independent.
	 * Note that scheduled tasks don't run in parallel by default. 
	 * So even if we used fixedRate, the next task won't be invoked until the previous one is done.
	 */
	@Scheduled(fixedRate = 5000)
	public void fixedRate() {
		System.out.println("Fixed rate task :"+dateFormat.format(new Date()));
	}
	
	/**
	 * In this case, the duration between the end of the last execution and the start of the next execution is fixed. 
	 * The task always waits until the previous one is finished.
	 * This option should be used when it’s mandatory that the previous execution is completed before running again.
	 */
	@Scheduled(fixedDelay = 1000)
	public void scheduleFixedDelayTask() {
	    System.out.println(
	      "Fixed delay task - " + dateFormat.format(new Date()));
	}
	
    /**
     * If we want to support parallel behavior in scheduled tasks, we need to add the @Async annotation
     * along with @EnableAsync at class level
     * @throws InterruptedException
     */
    @Async
    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        System.out.println(
          "Fixed rate task async - " + dateFormat.format(new Date()));
        Thread.sleep(2000);
    }
    
    /**
     * Both fixedDelay as well as initialDelay in this example. 
     * The task will be executed the first time after the initialDelay value, and it will continue to be executed according to the fixedDelay.
     * This option is convenient when the task has a setup that needs to be completed.
     */
    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void scheduleFixedRateWithInitialDelayTask() {
     
        System.out.println(
          "Fixed rate task with one second initial delay - " + dateFormat.format(new Date()));
    }
    
    /**
     * Sometimes delays and rates are not enough, and we need the flexibility of a cron expression to control the schedule of our tasks
     * we're scheduling a task to be executed at 10:15 AM on the 15th day of every month.
     * By default, Spring will use the server's local time zone for the cron expression. However, we can use the zone attribute to change this timezone
     * @Scheduled(cron = "0 15 10 15 * ?", zone = "Europe/Paris")
     */
    @Scheduled(cron = "0 15 10 15 * ?")
    public void scheduleTaskUsingCronExpression() {
     
        System.out.println(
          "schedule tasks using cron jobs - " + dateFormat.format(new Date()));
    }

}
