package zzpl.pojo.qo.user;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class CompanyQO extends BaseQo {
	/**
	 * 公司ID
	 */
	private String companyID;
	/**
	 * 公司名称
	 */
	private String companyName;

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
