//import java.text.ParseException;
//
//import org.quartz.CronTrigger;
//import org.quartz.Job;
//import org.quartz.JobDetail;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.quartz.SchedulerFactory;
//import org.quartz.impl.StdSchedulerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.quartz.JobDetailBean;
//
//
//public class TestJob implements Job {
//	
//	static Logger logger = LoggerFactory.getLogger(TestJob.class);
//	
//	static void sleep(long millis){
//		try {
//			Thread.sleep(millis);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	static void test() throws SchedulerException, ParseException{
//		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//		Scheduler scheduler = schedulerFactory.getScheduler();
//		
//		// 价格日历
//		String cronExpression1 = "0/1 * * * * ?";
//		logger.info("--------------{}定时任务启动[{}]--------------", "测试任务", cronExpression1);
//		JobDetail jobDatail = new JobDetailBean();
//		jobDatail.setName("testJob");
//		jobDatail.setJobClass(TestJob.class);
////		jobDatail.getJobDataMap().put("job", dateSalePriceCacheJob);
//		CronTrigger cronTrigger1 = new CronTrigger("testJob", Scheduler.DEFAULT_GROUP);
//		cronTrigger1.setCronExpression(cronExpression1);
//		if (scheduler.getJobDetail(jobDatail.getName(), jobDatail.getGroup()) == null) {
//			scheduler.scheduleJob(jobDatail, cronTrigger1);
//		} else {
//			logger.warn("job：{} 已存在", jobDatail.getName());
//		}
//		if (scheduler.getJobDetail(jobDatail.getName(), jobDatail.getGroup()) == null) {
//			scheduler.scheduleJob(jobDatail, cronTrigger1);
//		} else {
//			logger.warn("job：{} 已存在", jobDatail.getName());
//		}
//		
//		// 定时器启动
//		scheduler.start();
//		
//		sleep(3000);
//		logger.debug("删除JOB");
//		scheduler.deleteJob("testJob", Scheduler.DEFAULT_GROUP);
//		
//		System.out.println(scheduler.getJobDetail(jobDatail.getName(), jobDatail.getGroup()));
//		
//
//		String cronExpression2 = "0/2 * * * * ?";
//
//		JobDetail jobDatail2 = new JobDetailBean();
//		jobDatail2.setName("testJob");
//		jobDatail2.setJobClass(TestJob.class);
////		jobDatail.getJobDataMap().put("job", dateSalePriceCacheJob);
//		CronTrigger cronTrigger2 = new CronTrigger("testJob", Scheduler.DEFAULT_GROUP);
//		cronTrigger2.setCronExpression(cronExpression2);
//		if (scheduler.getJobDetail(jobDatail2.getName(), jobDatail2.getGroup()) == null) {
//			scheduler.scheduleJob(jobDatail2, cronTrigger2);
//		} else {
//			logger.warn("job：{} 已存在", jobDatail2.getName());
//		}
//		if (scheduler.getJobDetail(jobDatail2.getName(), jobDatail2.getGroup()) == null) {
//			scheduler.scheduleJob(jobDatail2, cronTrigger2);
//		} else {
//			logger.warn("job：{} 已存在", jobDatail2.getName());
//		}
//	}
//	
//	public static void main(String[] args) throws SchedulerException, ParseException {
//		
//
//		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//		Scheduler scheduler = schedulerFactory.getScheduler();
//		scheduler.deleteJob("", "");
//		
//		
//	}
//
//	@Override
//	public void execute(JobExecutionContext context) throws JobExecutionException {
//		// TODO Auto-generated method stub
//		System.out.println("每秒执行："+System.currentTimeMillis());
//	}
//
//
//}
