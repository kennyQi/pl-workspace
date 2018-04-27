//package slfx.admin.component.job;
//
//import java.text.ParseException;
//import hg.log.util.HgLogger;
//import org.quartz.CronTrigger;
//import org.quartz.Job;
//import org.quartz.JobDetail;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.quartz.SchedulerFactory;
//import org.quartz.impl.StdSchedulerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.quartz.JobDetailBean;
//import org.springframework.stereotype.Component;
//import slfx.xl.pojo.qo.SalePolicyLineQO;
//import slfx.xl.pojo.system.SalePolicyConstants;
//import slfx.xl.spi.inter.SalePolicyLineService;
//
///**
// * @类功能说明：价格政策更新状态定时任务
// * @公司名称：浙江汇购科技有限公司
// * @部门：技术部
// * @作者：chenyanshou
// * @创建时间：2014-12-25上午11:26:02
// * @版本：V1.0
// */
//@Component
//public class SalePolicyUpdateStatusJob implements InitializingBean, Job {
//
//	@Autowired
//	private SalePolicyLineService policySpi;
//	@Autowired
//	private HgLogger hgLogger;//MongoDB日志
//	/**
//	 * 对象key
//	 */
//	private static final String JOB_KEY = "SalePolicyStatusUpdateJob";
//	/**
//	 * 调度器工厂
//	 */
//	private SchedulerFactory schedulerFactory;
////	/**
////	 * 调度器
////	 */
////	private Scheduler scheduler;
//	/**
//	 * 定时时间
//	 */
//	private String executeTime = "00:00";
//	/**
//	 * 定时任务名称
//	 */
//	private String jobName = "每日更新价格政策任务";
//	
//	public void init() throws SchedulerException{
////		scheduler = new StdSchedulerFactory().getScheduler();//通过调度器工厂获取调度器
////		scheduler.start();//启动调度器
//		schedulerFactory = new StdSchedulerFactory();
//		try {
//			String[] executeTimes = executeTime.split(":");
//			String hour = executeTimes[0];
//			String minute = executeTimes[1];
//			configJob(hour, minute);
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Override
//	public void execute(JobExecutionContext context) throws JobExecutionException {
//		SalePolicyUpdateStatusJob job = (SalePolicyUpdateStatusJob) context.getJobDetail().getJobDataMap().get(JOB_KEY);
//		job.updateStatus();
//	}
//
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		init();
//	}
//	
//	/**
//	 * @方法功能说明: 设置定时任务 
//	 * @param hour
//	 * @param minute
//	 * @throws SchedulerException
//	 * @throws ParseException
//	 */
//	public void configJob(String hour, String minute) throws SchedulerException, ParseException {
//		String cronExpression = String.format("0 %s %s * * ?", minute, hour);//定时表达式
//		hgLogger.info(this.getClass(),"chenyanshuo","--------------{}定时器启动[{}]--------------", jobName, cronExpression);
//		// 获取定时器
//		Scheduler scheduler = schedulerFactory.getScheduler();
//		JobDetail jobDatail = new JobDetailBean();
//		jobDatail.setName(JOB_KEY);
//		jobDatail.setJobClass(this.getClass());
//		jobDatail.getJobDataMap().put(JOB_KEY, this);
//		// 新建一个cornTrigger
//		CronTrigger cronTrigger = new CronTrigger(JOB_KEY,Scheduler.DEFAULT_GROUP);
//		cronTrigger.setCronExpression(cronExpression);
//		if (scheduler.getJobDetail(jobDatail.getName(), jobDatail.getGroup()) == null) {
//			scheduler.scheduleJob(jobDatail, cronTrigger);
//		} else {
//			hgLogger.warn(this.getClass(),"chenyanshuo","job：{} 已存在", jobName, jobDatail.getName());
//		}
//		// 定时器启动
//		scheduler.start();
//	}
//	
//	public void updateStatus(){
//		hgLogger.info(this.getClass(),"chenyanshuo","--------------{}开始执行--------------", jobName);
//		try {
//			//已发布的价格政策更新
//			SalePolicyLineQO policyQo = new SalePolicyLineQO();
//			policyQo.setSalePolicyLineStatus(SalePolicyConstants.SALE_SUCCESS);
//			policySpi.updateStatus(policyQo);
//			
//			//已开始的价格政策更新
//			policyQo = new SalePolicyLineQO();
//			policyQo.setSalePolicyLineStatus(SalePolicyConstants.SALE_START);
//			policySpi.updateStatus(policyQo);
//		} catch (Exception e) {
//			hgLogger.error("chenyanshuo", "SalePolicyLineLocalService->updateStatus->exception:" + HgLogger.getStackTrace(e));
//		}
//	}
//}