package hg.fx.command.reserveRecord;

import hg.framework.common.base.BaseSPICommand;

/**
 * @author cangs
 */
@SuppressWarnings("serial")
public class AuditReserveRecordCommand extends BaseSPICommand {

	/** -1--已拒绝 */
	public static final Integer CHECK_STATUS_REFUSE = -1;
	/** 1--已通过 */
	public static final Integer CHECK_STATUS_PASS = 0;
	
	/**
	 * 里程余额变更记录ID
	 */
	private String reserveRecordID;

	/**
	 * 审核状态 -1--已拒绝 1--待审核 0--已通过
	 */
	private Integer checkStatus;
	
	/**
	 * 商户id
	 */
	private String distributorID;
	

	public String getDistributorID() {
		return distributorID;
	}

	public void setDistributorID(String distributorID) {
		this.distributorID = distributorID;
	}

	public String getReserveRecordID() {
		return reserveRecordID;
	}

	public void setReserveRecordID(String reserveRecordID) {
		this.reserveRecordID = reserveRecordID;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

}
