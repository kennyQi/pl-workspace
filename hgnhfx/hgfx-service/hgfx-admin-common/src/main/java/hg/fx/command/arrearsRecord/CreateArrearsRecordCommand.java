package hg.fx.command.arrearsRecord;

import hg.framework.common.base.BaseSPICommand;

/**
 * 创建可欠费里程修改记录命令
 * @author cangs
 */
@SuppressWarnings("serial")
public class CreateArrearsRecordCommand extends BaseSPICommand {

	/**
	 * 商户ID
	 */
	private String distributorID;

	/**
	 * 原可欠费里程（变更前）
	 */
	private Integer preArrears;

	/**
	 * 申请可欠费里程
	 */
	private Integer applyArrears;

	/**
	 * 申请理由
	 */
	private String reason;

	/**
	 * 处理人ID
	 */
	private String authUserID;

	public String getDistributorID() {
		return distributorID;
	}

	public void setDistributorID(String distributorID) {
		this.distributorID = distributorID;
	}

	public Integer getPreArrears() {
		return preArrears;
	}

	public void setPreArrears(Integer preArrears) {
		this.preArrears = preArrears;
	}

	public Integer getApplyArrears() {
		return applyArrears;
	}

	public void setApplyArrears(Integer applyArrears) {
		this.applyArrears = applyArrears;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAuthUserID() {
		return authUserID;
	}

	public void setAuthUserID(String authUserID) {
		this.authUserID = authUserID;
	}

}
