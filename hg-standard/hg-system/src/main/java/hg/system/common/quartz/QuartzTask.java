package hg.system.common.quartz;

/**
 * @类功能说明：定时任务
 * @类修改者：
 * @修改日期：2015-4-1下午3:31:47
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-1下午3:31:47
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
	 * @方法功能说明：执行
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-1下午3:39:29
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
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
