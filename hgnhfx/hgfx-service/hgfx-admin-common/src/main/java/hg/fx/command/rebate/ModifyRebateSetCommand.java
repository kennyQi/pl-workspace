package hg.fx.command.rebate;

import hg.framework.common.base.BaseSPICommand;

import java.util.Date;

public class ModifyRebateSetCommand  extends BaseSPICommand{
	
	/**  */
	private static final long serialVersionUID = 1L;

	/**
	 *  生效时间
	 * */
	private Date implementDate;
	
	/**
	 *  失效时间
	 * */
	private Date invalidDate;
	
	/**
	 * 是否有效
	 * Y-有效  N-无效
	 * */
	private Boolean isImplement;
	/** 是否存在待审核的数据
	 * Y-有  N-无
	 * */
	private Boolean isCheck;
	
	private String runningSetId;
	
	private Boolean isDefault;
	
	private String intervalStr;

	
	public String getRunningSetId() {
		return runningSetId;
	}

	public void setRunningSetId(String runningSetId) {
		this.runningSetId = runningSetId;
	}

	public Date getImplementDate() {
		return implementDate;
	}

	public void setImplementDate(Date implementDate) {
		this.implementDate = implementDate;
	}

	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	public Boolean getIsImplement() {
		return isImplement;
	}

	public void setIsImplement(Boolean isImplement) {
		this.isImplement = isImplement;
	}

	public Boolean getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getIntervalStr() {
		return intervalStr;
	}

	public void setIntervalStr(String intervalStr) {
		this.intervalStr = intervalStr;
	}
	

}
