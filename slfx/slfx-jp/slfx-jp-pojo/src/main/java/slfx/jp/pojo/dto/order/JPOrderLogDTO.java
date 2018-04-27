package slfx.jp.pojo.dto.order;

import java.util.Date;

import slfx.jp.pojo.dto.BaseJpDTO;

@SuppressWarnings("serial")
public class JPOrderLogDTO extends BaseJpDTO{

	/**
	 *日志操作时间
	 */
	private Date logDate;
	

	/**
	 * 操作名称
	 */
	private   String   logName;
	
	/**
	 * 操作人
	 */
	private    String     logWorker;
	
	/**
	 * 操作信息
	 */
	private    String     logInfo;
	
	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public String getLogWorker() {
		return logWorker;
	}

	public void setLogWorker(String logWorker) {
		this.logWorker = logWorker;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
	
}
