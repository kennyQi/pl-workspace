package plfx.mp.app.component.job;
//package plfx.mp.app.component.job;
//
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
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.quartz.JobDetailBean;
//import org.springframework.stereotype.Component;
//
///**
// * 门票APP定时任务
// * 
// * @author zhurz
// */
//@Component
//public class SlfxMpJob implements InitializingBean, Job {
//	private final static Logger logger = LoggerFactory.getLogger(SlfxMpJob.class);
//	
//	private SchedulerFactory schedulerFactory;
//	
//	/**
//	 * 每天0点执行一次
//	 */
//	@Autowired
//	private DeleteCacheJob deleteCacheJob;
//
//	/**
//	 * 每周日0点执行一次
//	 */
//	@Autowired
//	private ScenicSpotUpdateJob scenicSpotUpdateJob;
//	
//	protected void initJob() throws SchedulerException, ParseException {
//
//		schedulerFactory = new StdSchedulerFactory();
//		Scheduler scheduler = schedulerFactory.getScheduler();
//		
//		// 价格日历
//		String cronExpression1 = "0 0 0 * * ?";
//		logger.info("--------------{}定时任务启动[{}]--------------", "缓存清除", cronExpression1);
//		JobDetail jobDatail = new JobDetailBean();
//		jobDatail.setName("dateSalePriceCacheJob");
//		jobDatail.setJobClass(this.getClass());
//		jobDatail.getJobDataMap().put("job", deleteCacheJob);
//		CronTrigger cronTrigger1 = new CronTrigger("dateSalePriceCacheJob", Scheduler.DEFAULT_GROUP);
//		cronTrigger1.setCronExpression(cronExpression1);
//		if (scheduler.getJobDetail(jobDatail.getName(), jobDatail.getGroup()) == null) {
//			scheduler.scheduleJob(jobDatail, cronTrigger1);
//		} else {
//			logger.warn("job：{} 已存在", jobDatail.getName());
//		}
//		
//		// 景点同步
//		String cronExpression2 = "0 0 0 ? * SUN";
//		logger.info("--------------{}定时任务启动[{}]--------------", "景区同步", cronExpression2);
//		JobDetail jobDatai2 = new JobDetailBean();
//		jobDatai2.setName("scenicSpotUpdateJob");
//		jobDatai2.setJobClass(this.getClass());
//		jobDatai2.getJobDataMap().put("job", scenicSpotUpdateJob);
//		CronTrigger cronTrigger2 = new CronTrigger("scenicSpotUpdateJob", Scheduler.DEFAULT_GROUP);
//		cronTrigger2.setCronExpression(cronExpression2);
//		if (scheduler.getJobDetail(jobDatai2.getName(), jobDatai2.getGroup()) == null) {
//			scheduler.scheduleJob(jobDatai2, cronTrigger2);
//		} else {
//			logger.warn("job：{} 已存在", jobDatai2.getName());
//		}
//		
//		// 定时器启动
//		scheduler.start();
//	}
//
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		initJob();
//	}
//
//	@Override
//	public void execute(JobExecutionContext context) throws JobExecutionException {
//		Object obj = context.getJobDetail().getJobDataMap().get("job");
//		if (obj instanceof DeleteCacheJob) {
//			DeleteCacheJob job = (DeleteCacheJob) obj;
//			job.clear();
//		} else if (obj instanceof ScenicSpotUpdateJob) {
//			ScenicSpotUpdateJob job = (ScenicSpotUpdateJob) obj;
//			job.updateScenicSpot();
//		}
//	}
//
//}
