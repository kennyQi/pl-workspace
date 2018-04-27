package hg.system.common.quartz;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.JobDetailBean;

/**
 * @类功能说明：定时任务管理器
 * @类修改者：
 * @修改日期：2015-4-1下午3:44:05
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-1下午3:44:05
 */
public class QuartzTaskManager {
	
	private static Logger logger = LoggerFactory.getLogger(QuartzTaskManager.class);
	private static QuartzTaskManager manager;
	
	private static final String TASK_KEY = "task";
	
	private Scheduler scheduler;
	
	private QuartzTaskManager(){}
	
	public static class QuartzTaskExecutor implements Job {
		@Override
		public void execute(JobExecutionContext context)
				throws JobExecutionException {
			Object obj = context.getJobDetail().getJobDataMap().get(TASK_KEY);
			if (obj == null || !(obj instanceof QuartzTask))
				return;
			((QuartzTask) obj).execute();
		}
	}

	private void init() throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		scheduler = schedulerFactory.getScheduler();
		scheduler.start();
	}
	
	public synchronized static QuartzTaskManager getInstance() {
		if (manager == null) {
			manager = new QuartzTaskManager();
			try {
				manager.init();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
		return manager;
	}

	public synchronized void addTask(QuartzTask task) throws SchedulerException, ParseException {
		logger.info("--------------[{}]定时任务新增[{}]--------------" , task.getTaskName(), task.getCronExpression());
		JobDetail jobDatail = new JobDetailBean();
		jobDatail.setName(task.getTaskName());
		jobDatail.setJobClass(QuartzTaskExecutor.class);
		jobDatail.setGroup(task.getGroupName());
		jobDatail.getJobDataMap().put(TASK_KEY, task);
		CronTrigger cronTrigger = new CronTrigger(task.getTaskName(), task.getGroupName());
		cronTrigger.setCronExpression(task.getCronExpression());
		if (scheduler.getJobDetail(jobDatail.getName(), jobDatail.getGroup()) == null) {
			scheduler.scheduleJob(jobDatail, cronTrigger);
		} else {
			logger.warn("task：{} 已存在", jobDatail.getName());
		}
	}
	
	public synchronized void modifyTask(QuartzTask task) throws SchedulerException, ParseException {
		delTask(task);
		addTask(task);
	}
	
	public synchronized void delTask(QuartzTask task) throws SchedulerException {
		logger.info("--------------[{}]定时任务删除[{}]--------------" , task.getTaskName(), task.getCronExpression());
		scheduler.deleteJob(task.getTaskName(), task.getGroupName());
	}
	
}