package slfx.jp.command.admin.orderLog;

import java.io.Serializable;

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
	 * 价格政策ID
	 */
	private String jpOrderId;

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

	public String getJpOrderId() {
		return jpOrderId;
	}

	public void setJpOrderId(String jpOrderId) {
		this.jpOrderId = jpOrderId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
