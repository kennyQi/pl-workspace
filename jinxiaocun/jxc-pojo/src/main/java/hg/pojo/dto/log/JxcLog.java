package hg.pojo.dto.log;

import hg.log.po.normal.HgLog;

public class JxcLog extends HgLog {

	/**
	 * 操作人真实名称
	 */
	private String operatorName;
	
	/**
	 * 操作人类型
	 */
	private String operatorType;
	
	/**
	 * 操作人帐号
	 */
	private String operatorAccount;

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorAccount() {
		return operatorAccount;
	}

	public void setOperatorAccount(String operatorAccount) {
		this.operatorAccount = operatorAccount;
	}

}
