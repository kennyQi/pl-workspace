package hg.fx.command.reserveRecord;

import hg.framework.common.base.BaseSPICommand;


/**
 * 充值备付金command
 * 并创建里程变更记录
 * @author xinglj
 *
 */
@SuppressWarnings("serial")
public class CreateChargeReserveRecordCommand extends BaseSPICommand {

	 
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
	private Long increment;
	
	/**
	 * 明细类型  
	 * 1--交易  2--交易退款  3--后台备付金充值  4--在线备付金充值  5--月末返利
	 */
	private Integer type;
	
	 
	/**
	 * 截图证明
	 * type=3 此字段有值
	 */
	private String provePath;
	
	/**
	 * 处理人ID
	 */
	private String authUserID;
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

	public Long getIncrement() {
		return increment;
	}

	public void setIncrement(Long increment) {
		this.increment = increment;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
 
  
	public String getProvePath() {
		return provePath;
	}

	public void setProvePath(String provePath) {
		this.provePath = provePath;
	}

	public String getAuthUserID() {
		return authUserID;
	}

	public void setAuthUserID(String authUserID) {
		this.authUserID = authUserID;
	}
	
}
