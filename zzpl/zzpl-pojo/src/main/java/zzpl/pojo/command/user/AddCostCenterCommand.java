package zzpl.pojo.command.user;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class AddCostCenterCommand extends BaseCommand {
	/**
	 * 公司名称
	 */
	private String costCenterName;

	/**
	 * 公司ID
	 */
	private String companyID;

	/**
	 * 公司名称
	 */
	private String companyName;

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
