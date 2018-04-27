package hg.fx.command.arrearsRecord;

import hg.framework.common.base.BaseSPICommand;

/**
 * 审核可欠费里程命令
 * @author cangs
 */
@SuppressWarnings("serial")
public class AuditArrearsRecordCommand extends BaseSPICommand {
	/** -1--已拒绝 */
	public static final Integer CHECK_STATUS_REFUSE = -1;
	/** 0--已通过 */
	public static final Integer CHECK_STATUS_PASS = 0;

	/**
	 * 可欠费里程变更记录ID
	 */
	private String arrearsRecordID;
	/**
	 * 审核状态 
	 * -1--已拒绝 
	 * 1--待审核 
	 * 0--已通过
	 */
	private Integer checkStatus;

	

	public String getArrearsRecordID() {
		return arrearsRecordID;
	}

	public void setArrearsRecordID(String arrearsRecordID) {
		this.arrearsRecordID = arrearsRecordID;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

}
