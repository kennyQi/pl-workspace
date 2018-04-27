package plfx.xl.pojo.command.salepolicy;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：价格政策操作日志 
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenyanshou
 * @创建时间：2014年12月24日上午9:59:23
 * @版本：V1.0
 */
public class CreateSalePolicyWorkLogCommand extends BaseCommand{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 操作名称
	 */
	private String logName;
	
	/**
	 * 操作人
	 */
	private String workerName;
	
	/**
	 * 操作信息
	 */
	private String logInfoName;
	
	/**
	 * 价格政策ID
	 */
	private String policyId;
	
	public String getLogName() {
		return logName;
	}
	public void setLogName(String logName) {
		this.logName = logName == null ? null : logName.trim();
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName == null ? null : workerName.trim();
	}
	public String getLogInfoName() {
		return logInfoName;
	}
	public void setLogInfoName(String logInfoName) {
		this.logInfoName = logInfoName == null ? null : logInfoName.trim();
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId == null ? null : policyId.trim();
	}
	public String getWorkerName() {
		return workerName;
	}
	
}