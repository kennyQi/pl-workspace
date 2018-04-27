package hg.framework.common.base.quartz;

/**
 * 定时任务
 *
 * @author zhurz
 */
public abstract class QuartzTask {

	/**
	 * 执行时间表达式
	 */
	private String cronExpression;

	/**
	 * 任务组名称
	 */
	private String groupName;

	/**
	 * 任务名称
	 */
	private String taskName;

	/**
	 * 执行定时方法
	 */
	public abstract void execute();
	
	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}
