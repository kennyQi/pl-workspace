package plfx.jp.command.pub.gj;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class CreateGJJPOrderLogCommand extends BaseCommand {

	/**
	 * 分票平台机票订单ID
	 */
	private String platformOrderId;

	/**
	 * 操作名称
	 */
	private String logName;

	/**
	 * 操作人标识 为分销平台时是管理员ID 为经销商时是经销商CODE 为供应商时是供应商CODE 为系统调度时不用赋值
	 */
	private String logWorkerId;

	/**
	 * 操作人类型
	 */
	private Integer logWorkerType;

	/**
	 * 操作信息
	 */
	private String logInfo;

	public CreateGJJPOrderLogCommand() {

	}
	
	public CreateGJJPOrderLogCommand(String platformOrderId, String logName,
			String logWorkerId, Integer logWorkerType, String logInfo) {
		this.platformOrderId = platformOrderId;
		this.logName = logName;
		this.logWorkerId = logWorkerId;
		this.logWorkerType = logWorkerType;
		this.logInfo = logInfo;
	}


	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public String getLogWorkerId() {
		return logWorkerId;
	}

	public void setLogWorkerId(String logWorkerId) {
		this.logWorkerId = logWorkerId;
	}

	public Integer getLogWorkerType() {
		return logWorkerType;
	}

	public void setLogWorkerType(Integer logWorkerType) {
		this.logWorkerType = logWorkerType;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

}
