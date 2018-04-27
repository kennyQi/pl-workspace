package plfx.yeexing.pojo.dto.order;

import java.util.Date;

import plfx.jp.pojo.dto.BaseJpDTO;

/*****
 * 
 * @类功能说明：机票日志DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月9日下午2:55:13
 * @版本：V1.0
 *
 */
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
