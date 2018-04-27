package hg.fx.command.reserveRecord;

import hg.framework.common.base.BaseSPICommand;


/**
 * 充值备里程command
 * 并修改里程变更状态
 * @author zqq
 *
 */
@SuppressWarnings("serial")
public class ChargeUpdateReserveRecordCommand extends BaseSPICommand {

	 
	/** 3--(后台)备付金充值 */
	public static final Integer RECORD_TYPE_RECHARGE = CreateReserveRecordCommand.RECORD_TYPE_RECHARGE;
	/** 4--(在线)备付金充值  0.1版本无该类型*/
	public static final Integer RECORD_TYPE_RECHARGE_ONLINE = CreateReserveRecordCommand.RECORD_TYPE_RECHARGE_ONLINE;
 
	
	/**
	 * 商户ID
	 */
	private String distributorID;
	
	/**
	 * 变化额 
	 */
	private Integer increment;
	
	
	/**
	 * 审核命令
	 */
	private AuditReserveRecordCommand auditCmd;
	
	public AuditReserveRecordCommand getAuditCmd() {
		return auditCmd;
	}

	public void setAuditCmd(AuditReserveRecordCommand auditCmd) {
		this.auditCmd = auditCmd;
	}

	public String getDistributorID() {
		return distributorID;
	}

	public void setDistributorID(String distributorID) {
		this.distributorID = distributorID;
	}

	public Integer getIncrement() {
		return increment;
	}

	public void setIncrement(Integer increment) {
		this.increment = increment;
	}

	
}
