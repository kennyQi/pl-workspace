package plfx.jp.command.admin.jpOrderLog;

import java.io.Serializable;

/****
 * 
 * @类功能说明：机票下单日志command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月9日下午1:59:07
 * @版本：V1.0
 *
 */
public class CreateJPOrderLogCommand implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 操作名称
	 */
	private String logName;

	/**
	 * 操作人
	 */
	private String worker;

	/**
	 * 操作信息
	 */
	private String logInfo;

	
	/**
	 * 分票平台机票订单ID
	 */
	private String platformOrderId;
	/**
	 * 操作人标识 为分销平台时是管理员ID 为经销商时是经销商CODE 为供应商时是供应商CODE 为系统调度时不用赋值
	 */
	private String logWorkerId;

	/**
	 * 操作人类型
	 */
	private Integer logWorkerType;

	
	public CreateJPOrderLogCommand(String platformOrderId, String logName,
			String logWorkerId, Integer logWorkerType, String logInfo) {
		this.platformOrderId = platformOrderId;
		this.logName = logName;
		this.logWorkerId = logWorkerId;
		this.logWorkerType = logWorkerType;
		this.logInfo = logInfo;
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

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}
	
	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}


	public String getPlatformOrderId() {
		return platformOrderId;
	}


	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
