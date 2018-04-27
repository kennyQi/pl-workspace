package hg.framework.common.base.quartz;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

/**
 * 定时任务管理器
 *
 * @author zhurz
 */
public class QuartzTaskManager {
	
	private static Logger logger = LoggerFactory.getLogger(QuartzTaskManager.class);
	private static QuartzTaskManager manager;
	
	private static final String TASK_KEY = "task";
	
	private Scheduler scheduler;
	
	private QuartzTaskManager() {
	}
	
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
		logger.info("--------------[{}]定时任务新增[{}]--------------", task.getTaskName(), task.getCronExpression());
		JobDetailImpl jobDatail = new JobDetailImpl();
		jobDatail.setName(task.getTaskName());
		jobDatail.setJobClass(QuartzTaskExecutor.class);
		jobDatail.setGroup(task.getGroupName());
		jobDatail.getJobDataMap().put(TASK_KEY, task);
		Trigger cronTrigger = TriggerBuilder
				.newTrigger()
				.forJob(task.getTaskName(), task.getGroupName())
				.withSchedule(CronScheduleBuilder.cronSchedule(task.getCronExpression()))
				.build();
		if (scheduler.getJobDetail(new JobKey(jobDatail.getName(), jobDatail.getGroup())) == null) {
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
		logger.info("--------------[{}]定时任务删除[{}]--------------", task.getTaskName(), task.getCronExpression());
		scheduler.deleteJob(new JobKey(task.getTaskName(), task.getGroupName()));
	}

	public static void main(String[] args) throws ParseException, SchedulerException {
		QuartzTask quartzTask = new QuartzTask() {
			@Override
			public void execute() {
				System.out.println("执行了一次");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		quartzTask.setCronExpression("0/1 * * * * ?");
		quartzTask.setTaskName("testTask");
		quartzTask.setGroupName("testGroup");
		getInstance().addTask(quartzTask);
	}
	
}